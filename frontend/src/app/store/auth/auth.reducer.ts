import { createReducer, on } from "@ngrx/store";
import { loginFailure, loginSuccess } from "./auth.actions";
import { AuthState, initialState } from "./auth.state";

export const authReducer = createReducer(
  initialState,
  on(
    loginSuccess,
    (state, {auth}): AuthState => ({
      ...state,
      auth
    })
  ),
  on(loginFailure, (state): AuthState => state)
);
