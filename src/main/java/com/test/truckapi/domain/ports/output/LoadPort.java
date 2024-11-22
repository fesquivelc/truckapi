package com.test.truckapi.domain.ports.output;

import com.test.truckapi.domain.entities.Load;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface LoadPort {
    Mono<Load> findLoadById(UUID loadId);
    Mono<Load> saveLoad(Load load);
    Flux<Load> finLoadsByTruckId(UUID truckId);
}
