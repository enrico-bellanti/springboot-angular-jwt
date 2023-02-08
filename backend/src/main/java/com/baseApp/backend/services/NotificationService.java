package com.baseApp.backend.services;

import com.baseApp.backend.exceptions.NotificationException;
import com.baseApp.backend.exceptions.UserException;
import com.baseApp.backend.models.Notification;
import com.baseApp.backend.models.NotificationEvent;
import com.baseApp.backend.models.User;
import com.baseApp.backend.repositories.NotificationRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    UserService userService;


    public Notification create(String type, User notifiable, Object data){

        Notification notification = new Notification(
                type,
                notifiable,
                data
        );

        return notificationRepository.save(notification);
    }

    public Notification create(NotificationEvent event){
        User user = userService.getById(event.getNotifiable_id())
                .orElseThrow(() -> new UserException("user_not_found", event.getNotifiable_id().toString()));

        Notification notification = new Notification(
                event.getType(),
                user,
                event.getData()
        );
        return notificationRepository.save(notification);
    }

    public Optional<Notification> getById(UUID id){
        return notificationRepository.findById(id);
    }

    public Notification setNotificationRead(Notification notification){
        if (notification.getRead_at() == null){
            notification.setRead_at(LocalDateTime.now());
            notification = notificationRepository.save(notification);
        }
        return notification;
    }

}
