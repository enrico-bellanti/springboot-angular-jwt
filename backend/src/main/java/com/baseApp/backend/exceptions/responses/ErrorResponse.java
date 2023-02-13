package com.baseApp.backend.exceptions.responses;

import java.time.ZonedDateTime;

public record ErrorResponse(String message, String error, Integer status, String path) {
}
