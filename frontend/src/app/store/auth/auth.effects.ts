import { AuthService } from './../../services/auth.service';
import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import {
  login,
  loginFailure,
  loginSuccess,
  logout,
  logoutSuccess,
  logoutFailure,
  refreshToken,
  refreshTokenSuccess,
  refreshTokenFailure
} from './auth.actions';
import { catchError, map, switchMap, tap } from 'rxjs/operators';
import { of, EMPTY } from 'rxjs';
import { Router } from '@angular/router';

@Injectable()
export class AuthEffects {
  doLogin = createEffect(() => this.actions$.pipe(
    ofType(login),
    switchMap((action) => {
      return this.authService.loginUser({...action.credentials})
        .pipe(
          map((auth) => loginSuccess({auth})),
          catchError(() => of(loginFailure()))
        )
    })
  ));

  doLogout = createEffect(() => this.actions$.pipe(
    ofType(logout),
    switchMap(() => {
      return this.authService.logoutUser()
        .pipe(
          map(() => logoutSuccess()),
          catchError(() => of(logoutFailure()))
        )
    })
  ));

  logoutSuccess = createEffect(() => this.actions$.pipe(
    ofType(logoutSuccess),
    tap(() => this.router.navigate(['/'])),
  ),
  {dispatch: false}
  );

  doRefreshToken = createEffect(() => this.actions$.pipe(
    ofType(refreshToken),
    switchMap((action) => {
      return this.authService.refreshToken(action.refreshToken)
        .pipe(
          map((auth) => refreshTokenSuccess({auth})),
          catchError(() => of(refreshTokenFailure()))
        )
    })
  ));

  refreshTokenFailure = createEffect(() => this.actions$.pipe(
    ofType(refreshTokenFailure),
    map(() => logoutSuccess())
  ));

  constructor(
    private actions$: Actions,
    private authService: AuthService,
    private router: Router,
  ) {}
}
