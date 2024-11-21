package com.test.truckapi.infrastructure.web.mapper;

import com.test.truckapi.domain.entities.Truck;
import com.test.truckapi.domain.enums.TruckStatus;
import com.test.truckapi.infrastructure.web.dto.CreateTruckRequestDTO;
import com.test.truckapi.infrastructure.web.dto.TruckResponseDTO;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class DtoMapper {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    public Truck toDomain(CreateTruckRequestDTO dto) {
        return Truck.builder()
                .licensePlate(dto.getLicensePlate())
                .model(dto.getModel())
                .capacityLimit(dto.getCapacityLimit())
                .currentLoad(0.0)
                .status(TruckStatus.UNLOADED)
                .build();
    }

    public TruckResponseDTO toDto(Truck truck) {
        return TruckResponseDTO.builder()
                .id(truck.id().toString())
                .licensePlate(truck.licensePlate())
                .model(truck.model())
                .capacityLimit(truck.capacityLimit())
                .currentLoad(truck.currentLoad())
                .status(truck.status().name())
                .build();
    }
//
//    public Load toDomain(LoadRequestDTO dto, UUID truckId) {
//        return Load.builder()
//                .id(UUID.randomUUID())
//                .truckId(truckId)
//                .volume(dto.getVolume())
//                .description(dto.getDescription())
//                .loadTimestamp(LocalDateTime.now())
//                .build();
//    }
//
//    public LoadResponseDTO toDto(Load load) {
//        return LoadResponseDTO.builder()
//                .id(load.getId().toString())
//                .truckId(load.getTruckId().toString())
//                .volume(load.getVolume())
//                .description(load.getDescription())
//                .loadTimestamp(formatDateTime(load.getLoadTimestamp()))
//                .unloadTimestamp(load.getUnloadTimestamp() != null ?
//                        formatDateTime(load.getUnloadTimestamp()) : null)
//                .build();
//    }
//
//    private String formatDateTime(LocalDateTime dateTime) {
//        return dateTime != null ? dateTime.format(DATE_FORMATTER) : null;
//    }
}
