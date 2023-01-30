package com.baseApp.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;

import static com.baseApp.backend.utils.TranslateUtils.tl;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserException extends RuntimeException{

    public UserException(String message) {
        super(tl(message));
    }

    public UserException(String message, Object ...args) {
        super(tl(message, Arrays.stream(args).toArray(Object[]::new)));
    }
}
