import { mergeMap, switchMap } from 'rxjs/operators';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { map } from 'rxjs';
import { activateUser, activateUserFailure, activateUserSuccess } from 'src/app/store/auth/auth.actions';
import { Actions, ofType } from '@ngrx/effects';
import { Store } from '@ngrx/store';
import { AuthState } from 'src/app/store/auth/auth.state';

export interface IActivateParams {
  token: string;
}


@Component({
  selector: 'app-activate-user',
  templateUrl: './activate-user.component.html',
  styleUrls: ['./activate-user.component.scss']
})
export class ActivateUserComponent implements OnInit {

  constructor(
    private activatedRoute: ActivatedRoute,
    private store: Store<AuthState>,
  ) { }

  ngOnInit(): void {
    this.activatedRoute.queryParams
    .subscribe((params) => {
      if (!!params && params['token']) {
        const token = params['token'];
        this.store.dispatch(activateUser({token}));
      }
    })

  }

}
