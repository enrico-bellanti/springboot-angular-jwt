import { map } from 'rxjs';
import { createReducer, on } from "@ngrx/store";
import { loadNotificationsSuccess, setNotificationReadSuccess } from "./notification.actions";
import { initialState, NotificationState } from "./notification.state";

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
  )
);
