import { BehaviorSubject, map, mergeMap, Subject, takeUntil, tap } from 'rxjs';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Actions, ofType } from '@ngrx/effects';
import { Store } from '@ngrx/store';
import { login, loginFailure } from 'src/app/store/auth/auth.actions';
import { AuthState } from 'src/app/store/auth/auth.state';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, OnDestroy {
  private destroy$$ = new Subject<void>();
  errorLoginMessage$$: Subject<string | null> = new Subject();

  constructor(
    private readonly fb: FormBuilder,
    private store: Store<AuthState>,
    private actions$: Actions,
  ) {}

  loginForm: FormGroup = this.fb.group({
    email: [null, Validators.required],
    password: [null, [Validators.required, Validators.minLength(6)]],
  });

  ngOnInit(): void {
    this.actions$.pipe(
      takeUntil(this.destroy$$),
      ofType(loginFailure)
    ).subscribe((action) => this.errorLoginMessage$$.next(action.error.message))
  }

  onSubmit(){
    this.errorLoginMessage$$.next(null)
    const credentials = {
      email: this.email?.value,
      password: this.password?.value
    }


    this.store.dispatch(login({credentials}));
  }

  get email() {
    return this.loginForm.get('email');
  }

  get password() {
    return this.loginForm.get('password');
  }

  ngOnDestroy() {
    this.destroy$$.next();
  }

}
