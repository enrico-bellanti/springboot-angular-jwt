package com.baseApp.backend.payloads.responses;

import lombok.Builder;

@Builder
public record BodyResponse<String, T>(String message, T data) {

}
