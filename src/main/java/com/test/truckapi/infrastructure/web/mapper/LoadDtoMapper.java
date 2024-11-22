package com.test.truckapi.infrastructure.web.mapper;

import com.test.truckapi.domain.entities.Load;
import com.test.truckapi.infrastructure.web.dto.LoadRequestDTO;
import com.test.truckapi.infrastructure.web.dto.LoadResponseDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class LoadDtoMapper {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    public Load toDomain(LoadRequestDTO dto, UUID truckId) {
        return Load.builder()
                .truckId(truckId)
                .volume(dto.getVolume())
                .description(dto.getDescription())
                .loadTimestamp(LocalDateTime.now())
                .build();
    }

    public LoadResponseDTO toDto(Load load) {
        return LoadResponseDTO.builder()
                .id(load.getId())
                .status(load.getStatus().name())
                .volume(load.getVolume())
                .description(load.getDescription())
                .loadTimestamp(load.getLoadTimestamp())
                .unloadTimestamp(load.getUnloadTimestamp())
                .build();
    }
}
