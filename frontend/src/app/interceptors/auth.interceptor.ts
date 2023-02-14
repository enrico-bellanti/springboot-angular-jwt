import { switchMap } from 'rxjs/operators';
import { refreshToken, refreshTokenSuccess, refreshTokenFailure, logout } from './../store/auth/auth.actions';
import { getAuthInfo } from './../store/auth/auth.selectors';
import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { catchError, first, mergeMap, Observable, of, throwError } from 'rxjs';
import { AuthState } from '../store/auth/auth.state';
import { Store } from '@ngrx/store';
import { Actions, ofType } from '@ngrx/effects';

const TOKEN_HEADER_KEY = 'Authorization';
const TOKEN_TYPE = 'Bearer'

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(
    private store: Store<AuthState>,
    private actions$: Actions,
    ) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return this.store.select(getAuthInfo).pipe(
      first(),
      mergeMap(authInfo => {
        const token = authInfo?.accessToken;
        const authReq = !!token ?
          this.addTokenHeader(request, token) :
          request;
        return next.handle(authReq).pipe(
          catchError(err => {
            if (
              err instanceof HttpErrorResponse &&
              !!authInfo &&
              err.status === 401 &&
              err.error.error === "ExpiredJwtException"
              ) {
              return this.handle401Error(request, next, authInfo.refreshToken, err);
            }

            return throwError(() => err)
          })
        );
      }),
    );
  }

  private handle401Error(request: HttpRequest<any>, next: HttpHandler, refreshTokenString: string, error:HttpErrorResponse){
    this.store.dispatch(refreshToken({refreshToken: refreshTokenString}));
    return this.actions$.pipe(
      ofType(refreshTokenSuccess, refreshTokenFailure),
      mergeMap((action) => {
        if (action.type === refreshTokenSuccess.type) {
          console.log('REFRESH TOKEN RENEW');

          return next.handle(this.addTokenHeader(request, action.auth.accessToken));
        } else {
          console.log('REFRESH TOKEN EXPIRED');
          this.store.dispatch(logout());
          return throwError(() => error)
        }

      })
    )
  }


  private addTokenHeader(request: HttpRequest<any>, token: string) {
    return request.clone({ headers: request.headers.set(TOKEN_HEADER_KEY, `${TOKEN_TYPE} ${token}`) })
  }

}
