package com.baseApp.backend.configs.kafka;

import com.baseApp.backend.models.NotificationEvent;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-server}")
    private String bootstrapServer;

    public Map<String, Object> producerConfig(){
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        //props.put(JsonSerializer.TYPE_MAPPINGS, "event:com.baseApp.backend.models.NotificationEvent");
        return props;
    }

    @Bean
    public ProducerFactory<String, NotificationEvent> producerFactory(){
        TypeReference<NotificationEvent> typeReference = new TypeReference<>() {};
        return new DefaultKafkaProducerFactory<>(producerConfig(), new StringSerializer(), new JsonSerializer<>(typeReference));
    }

    @Bean
    public KafkaTemplate<String, NotificationEvent> notificationKafkaTemplate(
            ProducerFactory<String, NotificationEvent> producerFactory
    ){
        return new KafkaTemplate<>(producerFactory);
    }
}
