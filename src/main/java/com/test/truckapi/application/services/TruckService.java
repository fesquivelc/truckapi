package com.test.truckapi.application.services;

import com.test.truckapi.domain.entities.Load;
import com.test.truckapi.domain.entities.Truck;
import com.test.truckapi.domain.enums.TruckStatus;
import com.test.truckapi.domain.exceptions.TruckNotFoundException;
import com.test.truckapi.domain.exceptions.TruckNotUnloadedException;
import com.test.truckapi.domain.ports.input.TruckServicePort;
import com.test.truckapi.domain.ports.output.TruckPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TruckService implements TruckServicePort {
    private final TruckPort truckPort;

    @Override
    public Mono<Truck> registerTruck(Truck truck) {
        return truckPort.saveTruck(truck);
    }

    @Override
    public Mono<Void> deleteTruck(UUID truckId) {
        return truckPort.findTruckById(truckId)
                .switchIfEmpty(buildNotFoundTruckException(truckId))
                .flatMap(this::resolveDeleteTruck);
    }

    private Mono<Void> resolveDeleteTruck(Truck truck) {
        if (truck.status() != TruckStatus.UNLOADED) {
            log.error("[TruckNotUnloaded] id={}", truck.id());
            return Mono.error(
                    new TruckNotUnloadedException(String.format("Truck with id=%s has loads", truck.id()))
            );
        }
        return truckPort.deleteTruck(truck.id());
    }

    private static <T> Mono<T> buildNotFoundTruckException(UUID truckId) {
        return Mono.defer(() -> {
            log.error("[TruckNotFound] id={}", truckId);
            return Mono.error(
                    new TruckNotFoundException(String.format("Truck with id=%s not found", truckId))
            );
        });
    }

    @Override
    public Flux<Truck> getAllTrucks() {
        return truckPort.getAllTrucks();
    }

    @Override
    public Mono<Truck> getTruckById(UUID truckId) {
        return truckPort.findTruckById(truckId)
                .switchIfEmpty(buildNotFoundTruckException(truckId));
    }
}
