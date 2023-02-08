import { createFeatureSelector, createSelector } from "@ngrx/store";
import { AuthState } from "./auth.state";

export const getAuthState = createFeatureSelector<AuthState>('auth');

export const getAuthInfo = createSelector(
  getAuthState,
  state => state.auth
);

export const getAccessToken = createSelector(
  getAuthInfo,
  (auth) => auth?.accessToken
);

export const getRefreshToken = createSelector(
  getAuthInfo,
  (auth) => auth?.refreshToken
);

export const getUserId = createSelector(
  getAuthInfo,
  (auth) => auth?.id
);

export const getUserEmail = createSelector(
  getAuthInfo,
  (auth) => auth?.email
);

export const getUserRoles = createSelector(
  getAuthInfo,
  (auth) => auth?.roles
);

