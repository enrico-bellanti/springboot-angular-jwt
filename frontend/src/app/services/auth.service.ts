import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { ILoginForm } from '../interfaces/ilogin-form';
import { IRegistrationForm } from '../interfaces/iregistration-form';
import { IAuth } from '../interfaces/iauth';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  apiBaseUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  registerUser(user: IRegistrationForm) {
    return this.http.post(`${this.apiBaseUrl}/auth/sign-up`, { user });
  }

  loginUser(credentials: ILoginForm): Observable<IAuth> {
    return this.http.post<IAuth>(
      `${this.apiBaseUrl}/auth/sign-in`, { ...credentials }
      );
  }

  refreshToken(refreshToken: string): Observable<IAuth> {
    return this.http.post<IAuth>(
      `${this.apiBaseUrl}/auth/refresh-token`, { refreshToken }
      );
  }

  logoutUser() {
    return this.http.delete(`${this.apiBaseUrl}/auth/sign-out`);
  }

}
