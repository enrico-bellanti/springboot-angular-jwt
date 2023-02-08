package com.baseApp.backend.services;

import com.baseApp.backend.models.Notification;
import com.baseApp.backend.payloads.responses.NotificationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendPrivateNotification(final String userId, Notification notification){
        NotificationResponse response =
                new NotificationResponse(notification);
        String destination = "/topic/private-notifications/" + notification.getNotifiable().getId();
        messagingTemplate.convertAndSend(destination, response);
    }
}
