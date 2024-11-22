package com.test.truckapi.domain.entities;

import com.test.truckapi.domain.enums.TruckStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Truck {
    private UUID id;
    private String licensePlate;
    private String model;
    private Double capacityLimit;
    private Double currentLoad;
    private TruckStatus status;
    private Long version;
}