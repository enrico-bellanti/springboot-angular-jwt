package com.baseApp.backend.payloads.responses;

import com.baseApp.backend.models.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponse {

    private UUID id;
    private Object data;

    private LocalDateTime read_at;

    public NotificationResponse(Notification notification) {
        this.id = notification.getId();
        this.data = notification.getData();
        this.read_at = notification.getRead_at();
    }
}
