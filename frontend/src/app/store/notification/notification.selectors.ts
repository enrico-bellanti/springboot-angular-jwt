import { createFeatureSelector, createSelector } from '@ngrx/store';
import { INotification } from 'src/app/interfaces/inotification';
import { NotificationState } from './notification.state';

export const getNotificationState = createFeatureSelector<NotificationState>('notification');

export const getNotifications = createSelector(
  getNotificationState,
  (state): INotification[] | null => state.notification.list
);

export const getUreadNotifications = createSelector(
  getNotifications,
  (notifications): INotification[] => notifications!.filter(n => n.read_at === null)
);
