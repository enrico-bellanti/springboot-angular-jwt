import { map } from 'rxjs/operators';
import { userIsAuthenticated } from './../store/auth/auth.selectors';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Store } from '@ngrx/store';
import { Observable, mergeMap } from 'rxjs';
import { AuthState } from '../store/auth/auth.state';

@Injectable({
  providedIn: 'root'
})
export class UserIsAccessAllowedGuard implements CanActivate {
  constructor(
    private store: Store<AuthState>,
    private router: Router
  ) { }

  canActivate(): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

      return this.store.select(userIsAuthenticated).pipe(
        map((isAuthenticated) => {
          if (!isAuthenticated) {
            this.router.navigate(['/']);
            return false
          } else {
            return true
          }
        })
      )
  }

}
