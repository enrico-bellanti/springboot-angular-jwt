import { setNotificationRead } from './../../store/notification/notification.actions';
import { INotification } from './../../interfaces/inotification';
import { mergeMap, Observable, Subject, map, tap, takeUntil } from 'rxjs';
import { NotificationState } from './../../store/notification/notification.state';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Actions, ofType } from '@ngrx/effects';
import { Store } from '@ngrx/store';
import { getNotifications } from 'src/app/store/notification/notification.selectors';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.scss']
})
export class NotificationsComponent implements OnInit, OnDestroy {
  private destroy$$ = new Subject<void>();
  notifications: INotification[] = [];
  notificationOpened: string | null = null;

  constructor(
    private actions$: Actions,
    private store: Store<NotificationState>,
  ) {}

  ngOnInit(): void {
    this.store.select(getNotifications).pipe(
      takeUntil(this.destroy$$),
      tap(notifications => {
        if (!!notifications) {
          this.notifications = notifications
        }
      })
    ).subscribe();
  }

  onNotificationRead(notification: INotification){
    if (notification.read_at === null) {
      this.store.dispatch(setNotificationRead({notification}));
    }
  }

  setNotificationOpened(id: string){
    this.notificationOpened = id;
  }

  ngOnDestroy(): void {
    this.destroy$$.next();
  }

}
