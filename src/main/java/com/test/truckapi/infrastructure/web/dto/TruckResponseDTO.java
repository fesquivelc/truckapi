package com.test.truckapi.infrastructure.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TruckResponseDTO {
    private String id;
    private String licensePlate;
    private String model;
    private Double capacityLimit;
    private Double currentLoad;
    private String status;
}
