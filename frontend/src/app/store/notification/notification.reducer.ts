import { map } from 'rxjs';
import { createReducer, on } from "@ngrx/store";
import { addUreadNotification, loadNotificationsSuccess, setNotificationReadSuccess } from "./notification.actions";
import { initialState, NotificationState } from "./notification.state";
import { logoutSuccess } from '../auth/auth.actions';

export const notificationReducer = createReducer(
  initialState,
  on(
    loadNotificationsSuccess,
    (state, {notifications}): NotificationState => {
      return {
        ...state,
        notification: {
          list: [...state.notification.list, ...notifications]
        }
      }
    }
  ),
  on(
    setNotificationReadSuccess,
    (state, {notification}): NotificationState => {
      return {
        ...state,
        notification: {
          list: state.notification.list.map(n => n.id === notification.id ? notification : n)
        }
      }
    }
  ),
  on(
    addUreadNotification,
    (state, {notification}): NotificationState => {
      const newList = [...state.notification.list, notification];
      newList.sort((b, a) => new Date(a.created_at).getTime() - new Date(b.created_at).getTime())
      return {
        ...state,
        notification: {
          list: newList
        }
      }
    }
  ),
  on(logoutSuccess, (): NotificationState => initialState),
);
