import { webSocketConfig } from "../configs/websocket.config";
import { WebsocketService } from "../services/websocket.service";

export function rxStompFactory() {
  const rxStomp = new WebsocketService();
  rxStomp.configure(webSocketConfig);
  return rxStomp;
}
