import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  apiBaseUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  test(): Observable<any> {
    return this.http.get<any>(`${this.apiBaseUrl}/test/base`);
  }

}
