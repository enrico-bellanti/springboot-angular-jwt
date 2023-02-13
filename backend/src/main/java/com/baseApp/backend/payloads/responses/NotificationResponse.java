package com.baseApp.backend.payloads.responses;

import com.baseApp.backend.models.Notification;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponse {

    private UUID id;
    private Object data;

    private LocalDateTime read_at;

    private Date created_at;

    public NotificationResponse(Notification notification) {
        this.id = notification.getId();
        this.data = notification.getData();
        this.read_at = notification.getRead_at();
        this.created_at = notification.getCreatedAt();
    }
}
