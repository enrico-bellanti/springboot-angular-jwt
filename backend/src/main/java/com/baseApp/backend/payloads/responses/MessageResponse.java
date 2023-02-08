package com.baseApp.backend.payloads.responses;

import lombok.*;

import static com.baseApp.backend.utils.TranslateUtils.tl;

import java.util.Arrays;

@Setter
@Getter
@ToString
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
