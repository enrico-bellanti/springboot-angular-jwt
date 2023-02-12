import { IAuth } from '../../interfaces/iauth';
import { createAction, props } from "@ngrx/store";
import { ILoginForm } from 'src/app/interfaces/ilogin-form';
import { IRegistrationForm } from 'src/app/interfaces/iregistration-form';
import { IHttpResError } from 'src/app/interfaces/ihttp-res-error';

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
  '[AUTH] login failure',
  props<{error: IHttpResError}>()
);

//REGISTRATION
export const register = createAction(
  '[AUTH] register',
  props<{registration: IRegistrationForm}>()
);

export const registerSuccess = createAction(
  '[AUTH] register success'
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

//ACTIVATE USER
export const activateUser = createAction(
  '[AUTH] activate user',
  props<{token: string}>()
);

export const activateUserSuccess = createAction(
  '[AUTH] activate user success'
);

export const activateUserFailure = createAction(
  '[AUTH] activate user failure'
);
