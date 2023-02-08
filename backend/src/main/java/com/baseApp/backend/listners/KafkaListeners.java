package com.baseApp.backend.listners;

import com.baseApp.backend.exceptions.UserException;
import com.baseApp.backend.models.Mail;
import com.baseApp.backend.models.NotificationEvent;
import com.baseApp.backend.models.User;
import com.baseApp.backend.services.MailService;
import com.baseApp.backend.services.NotificationService;
import com.baseApp.backend.services.UserService;
import com.baseApp.backend.services.WebSocketService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaListeners {

    @Autowired
    NotificationService notificationService;

    @Autowired
    MailService mailService;

    @Autowired
    UserService userService;

    @Autowired
    WebSocketService webSocketService;


    @KafkaListener(
            topics = "${spring.broker-kafka.properties.broadcast-topic}",
            containerFactory = "notificationKafkaListenerContainerFactory"
    )
    public void processBroadcastNotification(NotificationEvent event) {
        log.info("Received content broadcast from Kafka: {}", event);
        User user = userService.getById(event.getNotifiable_id())
                .orElseThrow(() -> new UserException("user_not_found", event.getNotifiable_id().toString()));

        var notification =notificationService.create(event.getType(), user, event.getData());

        webSocketService.sendPrivateNotification(
                event.getNotifiable_id().toString(),
                notification
        );
    }

    @KafkaListener(
            topics = "${spring.broker-kafka.properties.email-topic}",
            containerFactory = "notificationKafkaListenerContainerFactory"
    )
    public void processEmailNotification(NotificationEvent event) {
        log.info("Received content email from Kafka: {}", event);

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<Mail> typeReference = new TypeReference<>() {};

        Mail mail = mapper.convertValue(event.getData(), typeReference);

        mailService.send(mail);
    }
}
