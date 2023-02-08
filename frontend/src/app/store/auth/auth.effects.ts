import { AuthService } from './../../services/auth.service';
import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { login, loginFailure, loginSuccess } from './auth.actions';
import { catchError, map, switchMap, tap } from 'rxjs/operators';
import { of, EMPTY } from 'rxjs';

@Injectable()
export class AuthEffects {
  doLogin = createEffect(() => this.actions$.pipe(
    ofType(login),
    switchMap((action) => {
      return this.authService.loginUser({...action.credentials})
        .pipe(
          tap(console.log),
          map((auth) => loginSuccess({auth})),
          catchError(() => of(loginFailure()))
        )
    })
  ));

  constructor(
    private actions$: Actions,
    private authService: AuthService
  ) {}
}
