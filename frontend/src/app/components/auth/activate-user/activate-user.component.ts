import { mergeMap, switchMap } from 'rxjs/operators';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { map, Subject, takeUntil } from 'rxjs';
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
export class ActivateUserComponent implements OnInit, OnDestroy {
  private destroy$$ = new Subject<void>();

  constructor(
    private activatedRoute: ActivatedRoute,
    private store: Store<AuthState>,
  ) { }

  ngOnDestroy(): void {
    this.destroy$$.next();
  }

  ngOnInit(): void {
    this.activatedRoute.queryParams
    .pipe(
      takeUntil(this.destroy$$)
    )
    .subscribe((params) => {
      if (!!params && params['token']) {
        const token = params['token'];
        this.store.dispatch(activateUser({token}));
      }
    })

  }

}
