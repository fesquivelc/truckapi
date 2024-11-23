package com.test.truckapi.infrastructure.adapters.repositories;

import com.test.truckapi.domain.entities.Truck;
import com.test.truckapi.domain.ports.output.TruckPort;
import com.test.truckapi.infrastructure.adapters.mappers.TruckMapper;
import com.test.truckapi.infrastructure.persistence.repositories.TruckReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TruckRepository implements TruckPort {
    private final TruckReactiveRepository truckReactiveRepository;
    private final TruckMapper truckMapper;

    @Override
    public Mono<Truck> saveTruck(Truck truck) {
        return Mono.just(truck)
                .map(truckMapper::toEntity)
                .flatMap(truckReactiveRepository::save)
                .map(truckMapper::toDomain);
    }

    @Override
    public Flux<Truck> getAllTrucks() {
        return truckReactiveRepository.findAllByIsDeletedFalse()
                .map(truckMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteTruck(UUID truckId) {
        return Mono.just(truckId)
                .flatMap(truckReactiveRepository::softDeleteById);
    }

    @Override
    public Mono<Truck> findTruckById(UUID truckId) {
        return truckReactiveRepository.findByIdAndIsDeletedFalse(truckId)
                .map(truckMapper::toDomain);
    }
}
