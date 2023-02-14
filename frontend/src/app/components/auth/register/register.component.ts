import { tap } from 'rxjs/operators';
import { Subject, takeUntil } from 'rxjs';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Store } from '@ngrx/store';
import { register, registerFailure } from 'src/app/store/auth/auth.actions';
import { AuthState } from 'src/app/store/auth/auth.state';
import { Actions, ofType } from '@ngrx/effects';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  private destroy$$ = new Subject<void>();
  errorLoginMessage$$: Subject<string | null> = new Subject();
  errorRegistrationMessage: string | null = null;

  constructor(
    private readonly fb: FormBuilder,
    private store: Store<AuthState>,
    private actions$: Actions,
  ) {}

  registerForm: FormGroup = this.fb.group({
    firstName: [null, Validators.required],
    lastName: [null, Validators.required],
    email: [null, Validators.required, Validators.email],
    password: [null, [Validators.required, Validators.minLength(6)]],
  });

  ngOnInit(): void {
    this.actions$.pipe(
      takeUntil(this.destroy$$),
      ofType(registerFailure)
    ).subscribe((action) => this.errorLoginMessage$$.next(action.error.message))

  }

  onSubmit(){
    this.errorLoginMessage$$.next(null)
    const registration = {
      firstName: this.firstName?.value,
      lastName: this.lastName?.value,
      email: this.email?.value,
      password: this.password?.value
    }


    this.store.dispatch(register({registration}));
  }

  get firstName() {
    return this.registerForm.get('firstName');
  }

  get lastName() {
    return this.registerForm.get('lastName');
  }

  get email() {
    return this.registerForm.get('email');
  }

  get password() {
    return this.registerForm.get('password');
  }

  ngOnDestroy() {
    this.destroy$$.next();
  }

}
