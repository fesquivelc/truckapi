package com.test.truckapi.domain.entities;

import com.test.truckapi.domain.enums.TruckStatus;
import lombok.Builder;

import java.util.UUID;

@Builder
public record Truck(
        UUID id,
        String licensePlate,
        String model,
        Double capacityLimit,
        Double currentLoad,
        TruckStatus status
) {
}