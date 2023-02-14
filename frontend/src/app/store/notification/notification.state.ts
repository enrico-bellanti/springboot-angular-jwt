import { INotification } from "src/app/interfaces/inotification"

export const notificationStateKey = 'notification';

export interface NotificationState {
  notification: {
    list: INotification[],
  }
}

export const initialState: NotificationState = {
  notification: {
    list: []
  }
}
