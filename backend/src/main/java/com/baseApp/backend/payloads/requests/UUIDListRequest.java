package com.baseApp.backend.payloads.requests;

import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class UUIDListRequest {
    private Set<UUID> list = new HashSet<>();
}
