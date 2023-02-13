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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.baseApp.backend.utils.TranslateUtils.tl;

import java.util.List;
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

    @GetMapping
    public ResponseEntity<BodyResponse> list(
            @RequestParam(value = "column", required = false, defaultValue = "id") String column,
            @RequestParam(value = "direction", required = false, defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "25") Integer size,
            @AuthenticationPrincipal UserDetailsImpl user
    ) {
        Sort sort = Sort.by(direction, column);
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        var bodyResponse = BodyResponse.builder()
                .message(tl("notification_list_success"))
                .data(notificationService.getAllById(pageRequest, user.getId()))
                .build();

        return ResponseEntity.ok().body(bodyResponse);

    }

    @GetMapping("/get-unread")
    public ResponseEntity setNotificationAsRead(@AuthenticationPrincipal UserDetailsImpl user) {

        BodyResponse bodyResponse = new BodyResponse(
                tl("notification_count_success"),
                notificationService.getAllUnReadByUserId(user.getId())
        );

        return ResponseEntity.ok().body(bodyResponse);
    }



}
