package com.baseApp.backend.controllers;

import com.baseApp.backend.exceptions.NotificationException;
import com.baseApp.backend.models.Notification;
import com.baseApp.backend.models.UserDetailsImpl;
import com.baseApp.backend.payloads.responses.BodyResponse;
import com.baseApp.backend.payloads.responses.NotificationResponse;
import com.baseApp.backend.repositories.NotificationRepository;
import com.baseApp.backend.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.baseApp.backend.utils.TranslateUtils.tl;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/set-as-read/{notificationId}")
    public ResponseEntity setNotificationAsRead(
            @PathVariable("notificationId") UUID notificationId,
            @AuthenticationPrincipal UserDetailsImpl user
    ) {

        var notification = notificationService.getById(notificationId)
                .orElseThrow(() -> new NotificationException("notification_not_found", notificationId.toString()));

        var notifiableId = notification.getNotifiable().getId();
        var userId = user.getId();

        if (!notifiableId.equals(userId)){
            throw new NotificationException("notification_not_updated", notificationId.toString());
        }

        if (notification.getRead_at() != null){
            throw new NotificationException("notification_already_read", notificationId.toString());
        }

        var notificationRead = notificationService.setNotificationRead(notification);

        BodyResponse bodyResponse = new BodyResponse(
                tl("notification_as_read", notification.getId().toString()),
                new NotificationResponse(notificationRead)
        );

        return ResponseEntity.ok().body(bodyResponse);
    }
}
