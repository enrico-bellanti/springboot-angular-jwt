import { switchMap, map, catchError } from 'rxjs/operators';
import { NotificationService } from 'src/app/services/notification.service';
import {
  loadNotifications,
  loadNotificationsFailure,
  loadNotificationsSuccess,
  setNotificationRead,
  setNotificationReadFailure,
  setNotificationReadSuccess } from './notification.actions';
import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { loginSuccess } from '../auth/auth.actions';
import { of, EMPTY } from 'rxjs';

@Injectable()
export class NotificationEffects {

  loadUnReadNotification = createEffect(() => this.actions$.pipe(
    ofType(loginSuccess),
    map(() => loadNotifications())
  ));

  loadAllNotification = createEffect(() => this.actions$.pipe(
    ofType(loadNotifications),
    switchMap(() => {
      return this.notificationService.getAllNotification()
        .pipe(
          map(({data}) => loadNotificationsSuccess({notifications: data.content})),
          catchError(() => of(loadNotificationsFailure()))
        )
    })
  ));

  setNotificationRead = createEffect(() => this.actions$.pipe(
    ofType(setNotificationRead),
    switchMap(({notification}) => {
      return this.notificationService.setNotificationRead(notification.id)
        .pipe(
          map(({data}) => setNotificationReadSuccess({notification: data})),
          catchError(() => of(setNotificationReadFailure()))
        )
    })
  ));

  constructor(
    private actions$: Actions,
    private notificationService: NotificationService
  ) {}

}
