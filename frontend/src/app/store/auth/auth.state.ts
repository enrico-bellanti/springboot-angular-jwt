import { IAuth } from './../../interfaces/iauth';

export interface AuthState {
  auth: IAuth | null
}

export const initialState: AuthState = {
  auth: null
}
