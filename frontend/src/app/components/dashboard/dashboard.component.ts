import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { getUserId, getUserRolesName, getUserEmail } from 'src/app/store/auth/auth.selectors';
import { AuthState } from 'src/app/store/auth/auth.state';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  userId$ = this.store.select(getUserId);
  userEmail$ = this.store.select(getUserEmail)
  roles$ = this.store.select(getUserRolesName);

  constructor(
    private store: Store<AuthState>
  ) { }

  ngOnInit(): void {
  }

}
