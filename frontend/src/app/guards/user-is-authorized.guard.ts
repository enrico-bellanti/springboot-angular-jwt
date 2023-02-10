import { PermissionService } from './../services/permission.service';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserIsAuthorizedGuard implements CanActivate {
  constructor(
    private router: Router,
    private permissionService: PermissionService
  ) { }

  canActivate(
    route: ActivatedRouteSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

      return this.permissionService.hasPermissions(route.data['permissions']) || this.router.navigate(['/'])

  }

}
