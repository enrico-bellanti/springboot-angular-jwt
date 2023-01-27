package com.baseApp.backend.exceptions.responses;

import java.time.ZonedDateTime;

public record ErrorResponse(String message, String throwable, int httpStatus, ZonedDateTime zonedDateTime) {
}
