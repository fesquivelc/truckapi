package com.test.truckapi.domain.ports.input;

import com.test.truckapi.domain.entities.Load;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface LoadServicePort {
    Mono<Void> loadTruck(UUID truckId, Load load);
    Mono<Void> unloadTruck(UUID truckId, UUID loadId);
    Flux<Load> getLoadsByTruck(UUID truckId);
}
