package com.test.truckapi.domain.entities;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record Load(
        UUID id,
        UUID truckId,
        double volume,
        String description,
        LocalDateTime loadTimestamp,
        LocalDateTime unloadTimestamp
) {
}
