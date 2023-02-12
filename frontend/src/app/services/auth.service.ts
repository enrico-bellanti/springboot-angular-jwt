import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { ILoginForm } from '../interfaces/ilogin-form';
import { IRegistrationForm } from '../interfaces/iregistration-form';
import { IAuth } from '../interfaces/iauth';
import { IHttpRes } from '../interfaces/ihttp-res';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  apiBaseUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  registerUser(registration: IRegistrationForm): Observable<IHttpRes<never>> {
    return this.http.post<IHttpRes<never>>(`${this.apiBaseUrl}/auth/sign-up`, { ...registration });
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

  logoutUser(): Observable<IHttpRes<never>> {
    return this.http.delete<IHttpRes<never>>(`${this.apiBaseUrl}/auth/sign-out`);
  }

  activateUser(token: string): Observable<IHttpRes<never>> {
    console.log(token);

    return this.http.get<IHttpRes<never>>(`${this.apiBaseUrl}/auth/activate/${token}`);
  }

}
