package com.baseApp.backend.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import static com.baseApp.backend.utils.TranslateUtils.tl;

import java.util.Arrays;

@Data
@Builder
@NoArgsConstructor
public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = tl(message);
    }

    public MessageResponse(String message, Object ...args) {
        this.message = tl(message, Arrays.stream(args).toArray(Object[]::new));
    }
}
