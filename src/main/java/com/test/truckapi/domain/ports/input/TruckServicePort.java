package com.test.truckapi.domain.ports.input;

import com.test.truckapi.domain.entities.Load;
import com.test.truckapi.domain.entities.Truck;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TruckServicePort {
    Mono<Truck> registerTruck(Truck truck);
    Mono<Void> deleteTruck(UUID truckId);
    Flux<Truck> getAllTrucks();
    Mono<Truck> getTruckById(UUID truckId);
}
