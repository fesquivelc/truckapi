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
                .build();
    }

    public LoadEntity toEntity(Load domain) {
        return LoadEntity.builder()
                .id(domain.id())
                .truckId(domain.truckId())
                .volume(domain.volume())
                .description(domain.description())
                .loadTimestamp(domain.loadTimestamp())
                .unloadTimestamp(domain.unloadTimestamp())
                .build();
    }
}
