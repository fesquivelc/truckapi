package com.test.truckapi.infrastructure.web.mapper;

import com.test.truckapi.domain.entities.Truck;
import com.test.truckapi.domain.enums.TruckStatus;
import com.test.truckapi.infrastructure.web.dto.CreateTruckRequestDTO;
import com.test.truckapi.infrastructure.web.dto.TruckResponseDTO;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class TruckDtoMapper {
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
                .id(truck.getId().toString())
                .licensePlate(truck.getLicensePlate())
                .model(truck.getModel())
                .capacityLimit(truck.getCapacityLimit())
                .currentLoad(truck.getCurrentLoad())
                .status(truck.getStatus().name())
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

//    public LoadResponseDTO toDto(Load load) {
//        return LoadResponseDTO.builder()
//                .id(load.getId().toString())
//                .volume(load.getVolume())
//                .description(load.getDescription())
//                .loadTimestamp(formatDateTime(load.getLoadTimestamp()))
//                .unloadTimestamp(load.getUnloadTimestamp() != null ?
//                        formatDateTime(load.getUnloadTimestamp()) : null)
//                .build();
//    }
}
