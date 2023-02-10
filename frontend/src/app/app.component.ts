import { Router } from '@angular/router';
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
  title = 'frontend';

  auth$ = this.store.select(getAuthInfo);

  constructor(
    private store: Store<AuthState>,
    private router: Router
  ) { }

  ngOnInit(): void {

  }

  login(){
    const credentials = {
      email: "user@mail.io",
      password: "password"
    }

    this.store.dispatch(login({credentials}));
  }

  logout(){
    this.store.dispatch(logout());
  }

  register(){

  }

}
