import { WebsocketService } from './../../services/websocket.service';
import { switchMap, map, catchError } from 'rxjs/operators';
import { NotificationService } from 'src/app/services/notification.service';
import {
  activateWebSocket,
  activateWebsocketFailure,
  activateWebsocketSuccess,
  addUreadNotification,
  loadNotifications,
  loadNotificationsFailure,
  loadNotificationsSuccess,
  setNotificationRead,
  setNotificationReadFailure,
  setNotificationReadSuccess } from './notification.actions';
import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { loginSuccess } from '../auth/auth.actions';
import { of, EMPTY, first } from 'rxjs';
import { Store } from '@ngrx/store';
import { NotificationState } from './notification.state';
import { AuthState } from '../auth/auth.state';
import { getUserId } from '../auth/auth.selectors';
import { INotification } from 'src/app/interfaces/inotification';

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

  onLoadNotificationSuccess = createEffect(() => this.actions$.pipe(
    ofType(loadNotificationsSuccess),
    map(() => activateWebSocket())
  ));

  onActivateWebSocket = createEffect(() => this.actions$.pipe(
    ofType(activateWebSocket),
    switchMap(() => {
      this.websocketService.activate();
      return this.websocketService.connected$.pipe(
        map(() => activateWebsocketSuccess()),
        catchError(() => of(activateWebsocketFailure()))
      )
    })
  ));

  onWebSocketActivated = createEffect(() => this.actions$.pipe(
    ofType(activateWebsocketSuccess),
    switchMap(() => {
      return this.store.select(getUserId).pipe(
        switchMap(userId => {
          return this.websocketService.subScribePrivateTopic(userId).pipe(
            map(message => {
              const notification: INotification = JSON.parse(message.body);
              return addUreadNotification({notification})
            })
          )
        })
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
    private notificationService: NotificationService,
    private websocketService: WebsocketService,
    private store: Store<AuthState>
  ) {}

}
