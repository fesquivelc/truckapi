package com.test.truckapi.domain.ports.output;

import com.test.truckapi.domain.entities.Truck;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TruckPort {
    Mono<Truck> saveTruck(Truck truck);
    Flux<Truck> getAllTrucks();
    Mono<Void> deleteTruck(UUID truckId);
    Mono<Truck> findTruckById(UUID truckId);
}
