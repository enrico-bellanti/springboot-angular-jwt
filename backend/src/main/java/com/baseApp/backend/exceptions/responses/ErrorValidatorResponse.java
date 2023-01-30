package com.baseApp.backend.exceptions.responses;

import java.time.ZonedDateTime;
import java.util.List;

public record ErrorValidatorResponse(List<String> messages, String throwable, int httpStatus,
                                     ZonedDateTime zonedDateTime) {
}
