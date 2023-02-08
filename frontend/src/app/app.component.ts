import { login } from './store/auth/auth.actions';
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

  constructor(
    private store: Store<AuthState>,
  ) { }

  ngOnInit(): void {

    const credentials = {
      email: "user@mail.io",
      password: "password"
    }

    this.store.dispatch(login({credentials}));
  }

}
