import { MetaReducer } from "@ngrx/store";
import { AuthState, authStateKey } from "./auth/auth.state";
import { NotificationState, notificationStateKey } from "./notification/notification.state";
import { storageMetaReducer } from "./storage.metareducer";

/**
 * @description Insert here the key of state's feature
 */
export interface AppState {
  readonly [authStateKey]: AuthState;
  readonly [notificationStateKey]: NotificationState;
}

export const metaReducers: MetaReducer<AppState>[] = [storageMetaReducer];
