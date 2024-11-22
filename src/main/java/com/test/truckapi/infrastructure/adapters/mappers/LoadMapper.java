package com.test.truckapi.infrastructure.adapters.mappers;

import com.test.truckapi.domain.entities.Load;
import com.test.truckapi.infrastructure.persistence.entities.LoadEntity;
import org.springframework.stereotype.Component;

@Component
public class LoadMapper {
    public Load toDomain(LoadEntity entity) {
        return Load.builder()
                .id(entity.getId())
                .truckId(entity.getTruckId())
                .volume(entity.getVolume())
                .description(entity.getDescription())
                .loadTimestamp(entity.getLoadTimestamp())
                .unloadTimestamp(entity.getUnloadTimestamp())
                .version(entity.getVersion())
                .build();
    }

    public LoadEntity toEntity(Load domain) {
        return LoadEntity.builder()
                .id(domain.getId())
                .truckId(domain.getTruckId())
                .volume(domain.getVolume())
                .description(domain.getDescription())
                .loadTimestamp(domain.getLoadTimestamp())
                .unloadTimestamp(domain.getUnloadTimestamp())
                .version(domain.getVersion())
                .build();
    }
}
