import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Store } from '@ngrx/store';
import { login } from 'src/app/store/auth/auth.actions';
import { AuthState } from 'src/app/store/auth/auth.state';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  errorLoginMessage: string | null = null;

  constructor(
    private readonly fb: FormBuilder,
    private store: Store<AuthState>,
  ) {}

  loginForm: FormGroup = this.fb.group({
    email: [null, Validators.required],
    password: [null, [Validators.required, Validators.minLength(6)]],
  });

  ngOnInit(): void {
  }

  onSubmit(){
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

}
