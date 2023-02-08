package com.baseApp.backend.controllers;

import com.baseApp.backend.exceptions.UserException;
import com.baseApp.backend.mail.templates.WelcomeMail;
import com.baseApp.backend.models.Mail;
import com.baseApp.backend.models.Notification;
import com.baseApp.backend.models.NotificationEvent;
import com.baseApp.backend.models.User;
import com.baseApp.backend.services.*;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    @Autowired
    BrokerService brokerService;

    @Autowired
    UserService userService;

    @Autowired
    MailService mailService;

    @Autowired
    WebSocketService webSocketService;

    @Autowired
    NotificationService notificationService;

    @GetMapping("/base")
    public ResponseEntity<?> simpleTest(){
        var user = userService.getById(UUID.fromString("f1795bf3-0906-4ea4-acd0-3bd39592a8f8"))
                .orElseThrow(()->new RuntimeException("failed"));
        return ResponseEntity.ok().body(user);
    }

    @PreAuthorize("@authorizationService.hasAllPermissions({'role.manage-permissions', 'role.view'})")
    @GetMapping("/auth")
    public ResponseEntity<?> authorizationTest(){
        return ResponseEntity.ok().body("Authorized");
    }

    @GetMapping("/kafka")
    public ResponseEntity<?> kafkaTest(){
        var user = userService.first().orElseThrow(() -> new UserException("user_not_found"));

        var mail = Mail.builder()
                .to("test@mail.io")
                .text("text etxt etxt etx")
                .subject("titolo")
                .recipientName("Mario")
                .build();

        var message = new SimpleMessage("this is a simple message");

        var eventMail = new NotificationEvent(
                "type-test",
                user.getId(),
                mail
        );

        var eventMessage = new NotificationEvent(
                "type-test",
                user.getId(),
                message
        );

        brokerService.notifyToKafka("email-topic", eventMail);
        brokerService.notifyToKafka("broadcast-topic", eventMessage);
        return ResponseEntity.ok().body("Kafka event sent");
    }

    @GetMapping("/mail")
    public ResponseEntity<?> mailTest(){
        var user = userService.first().orElseThrow(() -> new UserException("user_not_found"));
        var mail = Mail.builder()
                .to("test@mail.io")
                .text("text etxt etxt etx")
                .subject("titolo")
                .recipientName("Mario")
                .build();
        var event = new NotificationEvent(
                "type-test",
                user.getId(),
                mail
        );
        brokerService.notifyToKafka("email-topic", event);
        return ResponseEntity.ok().body("Mail sent");
    }

    @GetMapping("/mail-with-template")
    public ResponseEntity<?> mailTemplateTest(){
        var user = userService.first().orElseThrow(() -> new UserException("user_not_found"));

        Mail mail = new WelcomeMail(user);
        var event = new NotificationEvent(
                "type-test",
                user.getId(),
                mail
        );
        brokerService.notifyToKafka("email-topic", event);
        return ResponseEntity.ok().body("Mail sent");
    }

    @GetMapping("/socket")
    public ResponseEntity<?> socketTest(){
        var user = userService.first().orElseThrow(() -> new UserException("user_not_found"));

        var message = new SimpleMessage("this is a simple message");

        /*var notification = new Notification(
                "broadcast",
                user,
                message
        );*/

        var notification = notificationService.create("broadcast", user, message);

        webSocketService.sendPrivateNotification(user.getId().toString(), notification);

        return ResponseEntity.ok().body("Message sent");
    }


}
