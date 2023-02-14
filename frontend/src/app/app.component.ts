import { tap } from 'rxjs/operators';
import { getAuthInfo } from './store/auth/auth.selectors';
import { logout } from './store/auth/auth.actions';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Store } from '@ngrx/store';
import { AuthState } from './store/auth/auth.state';
import { getNotifications, getUreadNotifications } from './store/notification/notification.selectors';
import { Subject, takeUntil } from 'rxjs';
import { PermissionService } from './services/permission.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {
  private destroy$$ = new Subject<void>();

  notificationCount: number = 0;

  auth$ = this.store.select(getAuthInfo);

  constructor(
    private store: Store<AuthState>,
    private permissionService: PermissionService
  ) { }

  hasPermissionRoleController$ = this.permissionService.hasPermissions(['role.manage-permissions']);

  ngOnInit(): void {
    this.store.select(getUreadNotifications)
    .pipe(
      takeUntil(this.destroy$$),
      tap(unReadNotifications => {
        this.notificationCount = unReadNotifications.length;
      })
    ).subscribe()
  }

  logout(){
    this.store.dispatch(logout());
  }

  ngOnDestroy(): void {
    this.destroy$$.next();
  }

}
