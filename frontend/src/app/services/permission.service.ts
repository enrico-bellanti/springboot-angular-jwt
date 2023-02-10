import { IPermission } from './../interfaces/ipermission';
import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import { AuthState } from '../store/auth/auth.state';
import { getUserPermissions, getUserPermissionsName } from '../store/auth/auth.selectors';
import { map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PermissionService {

  constructor(
    private store: Store<AuthState>,
  ) { }

  hasPermissions(requiredPermissions: string[]): Observable<boolean>{
    return this.store.select(getUserPermissionsName).pipe(
      map((userPermissions) => requiredPermissions.some(permission => userPermissions.includes(permission)))
    )
  }
}
