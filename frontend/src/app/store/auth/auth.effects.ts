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
  refreshTokenFailure,
  register,
  registerSuccess,
  registerFailure,
  activateUser,
  activateUserSuccess,
  activateUserFailure
} from './auth.actions';
import { catchError, map, switchMap, tap } from 'rxjs/operators';
import { of, EMPTY } from 'rxjs';
import { Router } from '@angular/router';
import { IHttpResError } from 'src/app/interfaces/ihttp-res-error';

@Injectable()
export class AuthEffects {
  doLogin = createEffect(() => this.actions$.pipe(
    ofType(login),
    switchMap((action) => {
      return this.authService.loginUser({...action.credentials})
        .pipe(
          map((auth) => loginSuccess({auth})),
          catchError(({error}) => of(loginFailure({error})))
        )
    })
  ));

  loginSuccess = createEffect(() => this.actions$.pipe(
    ofType(loginSuccess),
    tap(() => this.router.navigate(['/dashboard'])),
  ),
  {dispatch: false}
  );

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

  doRegistration = createEffect(() => this.actions$.pipe(
    ofType(register),
    switchMap((action) => {
      return this.authService.registerUser({...action.registration})
        .pipe(
          map(() => registerSuccess()),
          catchError(({error}) => of(registerFailure({error})))
        )
    })
  ));

  registrationSuccess = createEffect(() => this.actions$.pipe(
    ofType(registerSuccess),
    tap(() => this.router.navigate(['/registration-success'])),
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

  activateUser = createEffect(() => this.actions$.pipe(
    ofType(activateUser),
    switchMap((action) => {
      return this.authService.activateUser(action.token)
        .pipe(
          map(() => activateUserSuccess()),
          catchError(() => of(activateUserFailure()))
        )
    })
  ));

  activateUserSuccess = createEffect(() => this.actions$.pipe(
    ofType(activateUserSuccess),
    tap(() => this.router.navigate(['registration/confirm/success'])),
  ),
  {dispatch: false}
  );

  activateUserFailure = createEffect(() => this.actions$.pipe(
    ofType(activateUserFailure),
    tap(() => this.router.navigate(['registration/confirm/failure'])),
  ),
  {dispatch: false}
  );

  constructor(
    private actions$: Actions,
    private authService: AuthService,
    private router: Router,
  ) {}
}
