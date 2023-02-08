package com.baseApp.backend.models;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationEvent {
    private String type;
    private UUID notifiable_id;
    private Object data;
}
