package com.baseApp.backend.exceptions;

import java.util.Arrays;

import static com.baseApp.backend.utils.TranslateUtils.tl;

public class AuthenticationException extends RuntimeException{
    public AuthenticationException(String message) {
        super(tl(message));
    }

    public AuthenticationException(String message, Object ...args) {
        super(tl(message, Arrays.stream(args).toArray(Object[]::new)));
    }
}
