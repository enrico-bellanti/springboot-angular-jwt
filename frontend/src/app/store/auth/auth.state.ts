import { IAuth } from './../../interfaces/iauth';

export const authStateKey = 'auth';

export interface AuthState {
  auth: IAuth | null
}

export const initialState: AuthState = {
  auth: null
}
