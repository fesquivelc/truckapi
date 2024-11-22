package com.test.truckapi.infrastructure.adapters.mappers;

import com.test.truckapi.domain.entities.Truck;
import com.test.truckapi.domain.enums.TruckStatus;
import com.test.truckapi.infrastructure.persistence.entities.TruckEntity;
import org.springframework.stereotype.Component;

@Component
public class TruckMapper {
    public Truck toDomain(TruckEntity entity) {
        return Truck.builder()
                .id(entity.getId())
                .licensePlate(entity.getLicensePlate())
                .model(entity.getModel())
                .capacityLimit(entity.getCapacityLimit())
                .currentLoad(entity.getCurrentLoad())
                .status(TruckStatus.valueOf(entity.getStatus()))
                .version(entity.getVersion())
                .build();
    }

    public TruckEntity toEntity(Truck domain) {
        return TruckEntity.builder()
                .id(domain.getId())
                .licensePlate(domain.getLicensePlate())
                .model(domain.getModel())
                .capacityLimit(domain.getCapacityLimit())
                .currentLoad(domain.getCurrentLoad())
                .status(domain.getStatus().name())
                .version(domain.getVersion())
                .build();
    }
}
