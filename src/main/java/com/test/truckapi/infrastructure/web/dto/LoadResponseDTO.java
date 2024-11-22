package com.test.truckapi.infrastructure.web.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class LoadResponseDTO {
    private UUID id;
    private String status;
    private double volume;
    private String description;
    private LocalDateTime loadTimestamp;
    private LocalDateTime unloadTimestamp;
}
