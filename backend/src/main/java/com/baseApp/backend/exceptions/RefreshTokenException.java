package com.baseApp.backend.exceptions;


import java.util.Arrays;
import static com.baseApp.backend.utils.TranslateUtils.tl;

public class RefreshTokenException extends RuntimeException {
    public RefreshTokenException(String message) {
        super(tl(message));
    }

    public RefreshTokenException(String message, Object ...args) {
        super(tl(message, Arrays.stream(args).toArray(Object[]::new)));
    }
}
