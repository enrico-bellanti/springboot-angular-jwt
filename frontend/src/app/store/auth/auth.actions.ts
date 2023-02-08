import { IAuth } from '../../interfaces/iauth';
import { createAction, props } from "@ngrx/store";
import { ILoginForm } from 'src/app/interfaces/ilogin-form';
import { IRegistrationForm } from 'src/app/interfaces/iregistration-form';

//LOGIN
export const login = createAction(
  '[AUTH] login',
  props<{credentials: ILoginForm}>()
);

export const loginSuccess = createAction(
  '[AUTH] login success',
  props<{auth: IAuth}>()
);

export const loginFailure = createAction(
  '[AUTH] login failure'
);

//REGISTRATION
export const register = createAction(
  '[AUTH] register',
  props<{registration: IRegistrationForm}>()
);

export const registerSuccess = createAction(
  '[AUTH] register success',
  props<{auth: IAuth}>()
);

export const registerFailure = createAction(
  '[AUTH] register failure'
);

//LOGOUT
export const logout = createAction(
  '[AUTH] logout'
);

export const logoutSuccess = createAction(
  '[AUTH] logout success'
);

export const logoutFailure = createAction(
  '[AUTH] logout failure'
);

//REFRESH TOKEN
export const refreshToken = createAction(
  '[AUTH] refresh token',
  props<{refreshToken: string}>()
);

export const refreshTokenSuccess = createAction(
  '[AUTH] refresh token success',
  props<{auth: IAuth}>()
);

export const refreshTokenFailure = createAction(
  '[AUTH] refresh token failure'
);
