import { INotification } from "src/app/interfaces/inotification"

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
