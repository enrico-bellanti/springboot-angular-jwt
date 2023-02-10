import { IPermission } from './../../interfaces/ipermission';
import { IRole } from './../../interfaces/irole';
import { IAuth } from './../../interfaces/iauth';
import { createFeatureSelector, createSelector } from "@ngrx/store";
import { AuthState } from "./auth.state";

export const getAuthState = createFeatureSelector<AuthState>('auth');

export const getAuthInfo = createSelector(
  getAuthState,
  (state): IAuth | null => state.auth
);

export const getAccessToken = createSelector(
  getAuthInfo,
  (auth): string => auth!.accessToken
);

export const userIsAuthenticated = createSelector(
  getAuthInfo,
  (auth): boolean => auth != null ? true : false
);

export const getRefreshToken = createSelector(
  getAuthInfo,
  (auth):string => auth!.refreshToken
);

export const getUserId = createSelector(
  getAuthInfo,
  (auth): string => auth!.id
);

export const getUserEmail = createSelector(
  getAuthInfo,
  (auth): string => auth!.email
);

export const getUserRoles = createSelector(
  getAuthInfo,
  (auth): IRole[] => auth!.roles
);

export const getUserPermissions = createSelector(
  getUserRoles,
  (roles): IPermission[]  => roles.map(role => role.permissions.flat()).flat()
);

export const getUserPermissionsName = createSelector(
  getUserPermissions,
  (permissions): string[]  => [
    ...new Set(permissions.map((permissions) => permissions.name))
  ]
);

