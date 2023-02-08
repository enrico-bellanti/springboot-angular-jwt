package com.baseApp.backend.services;

import com.baseApp.backend.models.NotificationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BrokerService {

    private KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    @Autowired
    public BrokerService(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void notifyToKafka(final String channel, NotificationEvent notificationEvent) {
        kafkaTemplate.send(channel, notificationEvent);
    }
}
