import { createAction, props } from "@ngrx/store";
import { INotification } from "src/app/interfaces/inotification";

export const loadUreadNotification = createAction(
  '[NOTIFICATION] load unread notitications'
);

export const loadUreadNotificationSuccess = createAction(
  '[NOTIFICATION] load unread notitications success',
  props<{notifications: INotification[]}>()
);

export const loadUreadNotificationFailure = createAction(
  '[NOTIFICATION] load unread notitications failure'
);

export const addUreadNotification = createAction(
  '[NOTIFICATION] add unread notitication',
  props<{notification: INotification}>()
);

export const setNotificationRead = createAction(
  '[NOTIFICATION] set notitication read',
  props<{notification: INotification}>()
);

export const setNotificationReadSuccess = createAction(
  '[NOTIFICATION] set notitication read seccess',
  props<{notification: INotification}>()
);

export const setNotificationReadFailure = createAction(
  '[NOTIFICATION] set notitication read failure'
);

export const loadNotifications = createAction(
  '[NOTIFICATION] load notitications'
);

export const loadNotificationsSuccess = createAction(
  '[NOTIFICATION] load notitications success',
  props<{notifications: INotification[]}>()
);

export const loadNotificationsFailure = createAction(
  '[NOTIFICATION] load notitications failure'
);



