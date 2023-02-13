import { getAuthInfo } from './store/auth/auth.selectors';
import { login, logout } from './store/auth/auth.actions';
import { Component, OnInit} from '@angular/core';
import { Store } from '@ngrx/store';
import { AuthState } from './store/auth/auth.state';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  notificationsHidden: boolean = true;
  notificationCount: number = 0;

  auth$ = this.store.select(getAuthInfo);

  constructor(
    private store: Store<AuthState>
  ) { }

  ngOnInit(): void {

  }

  logout(){
    this.store.dispatch(logout());
  }

  toggleBadgeVisibility() {
    this.notificationsHidden = !this.notificationsHidden;
  }

}
