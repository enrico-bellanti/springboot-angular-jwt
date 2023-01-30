package com.baseApp.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import static com.baseApp.backend.utils.TranslateUtils.tl;

import java.util.Arrays;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RoleException extends RuntimeException{
    public RoleException(String message) {
        super(tl(message));
    }

    public RoleException(String message, Object ...args) {
        super(tl(message, Arrays.stream(args).toArray(Object[]::new)));
    }
}
