import { notificationStateKey } from './notification/notification.state';
import { Action, ActionReducer, INIT, UPDATE } from '@ngrx/store';
import { authStateKey } from './auth/auth.state';
import { logoutSuccess } from './auth/auth.actions';
import { pick } from 'lodash';


/**
 * @description Map the permanentStore (the keys from state which we'd like to save in local storage)
 */

function storage() {
  return localStorage;
}
export const permanentStore = [
  authStateKey,
  notificationStateKey
];

function setSavedState(state: unknown, key: string) {
  storage().setItem(key, JSON.stringify(state));
}
function getSavedState(key: string) {
  let local = storage().getItem(key);
  if (typeof local === 'string') return JSON.parse(local);
  if (typeof local === null) return JSON.parse('');
}

// the keys from state which we'd like to save.
const stateKeys: string[] = [...permanentStore];
// the key for the local storage.
const localStorageKey = 'app_storage';

export function storageMetaReducer<S, A extends Action>(reducer: ActionReducer<S, A>): ActionReducer<S, A> {
  return (state: S | undefined, action: A): S => {
    // Questa logging out dopo che ha pulito ha in ascolto altri effetti
    if (action.type === logoutSuccess.type) {
      storage().clear();
      state = undefined;
    }

    // init the application state.
    if (action.type === INIT || action.type === UPDATE) {
      const savedState: S = getSavedState(localStorageKey);
      if (savedState) {
        try {
          return savedState;
        } catch {
          storage().clear();
        }
      }
    }
    const nextState = reducer(state, action);
    // save the next state to the application storage.
    const stateToSave = pick(nextState, stateKeys);
    setSavedState(stateToSave, localStorageKey);
    return nextState;
  };
}
