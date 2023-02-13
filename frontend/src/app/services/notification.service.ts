import { IPageble } from './../interfaces/ipageble';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IHttpRes } from '../interfaces/ihttp-res';
import { INotification } from '../interfaces/inotification';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private apiBaseUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getUnreadNotification(): Observable<IHttpRes<INotification[]>> {
    return this.http.get<IHttpRes<INotification[]>>(`${this.apiBaseUrl}/notification/get-unread`);
  }

  getAllNotification(): Observable<IHttpRes<IPageble<INotification[]>>> {
    return this.http.get<IHttpRes<IPageble<INotification[]>>>(`${this.apiBaseUrl}/notification`);
  }

  setNotificationRead(notificationId: string): Observable<IHttpRes<INotification>> {
    return this.http.get<IHttpRes<INotification>>(`${this.apiBaseUrl}/notification/set-as-read/${notificationId}`);
  }

}
