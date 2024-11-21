package com.test.truckapi.application.services;

import com.test.truckapi.domain.entities.Load;
import com.test.truckapi.domain.ports.input.LoadServicePort;
import com.test.truckapi.domain.ports.output.LoadPort;
import com.test.truckapi.domain.ports.output.TruckPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoadService implements LoadServicePort {
    private final TruckPort truckPort;
    private final LoadPort loadPort;

    @Override
    public Mono<Void> loadTruck(UUID truckId, Load load) {
//        Mono.defer(() -> {
//                            truckPort.findTruckById(truckId);
//                        }
//                )
//                .switchIfEmpty(Mono.error(new RuntimeException("Truck not found")))
//                .flatMap(truck -> {
//                    if (truck.status().equals("UNLOADED")) {
//                        return Mono.error(new RuntimeException("Truck not unloaded"));
//                    }
//                    return Mono.empty();
//                });
        return null;
    }

    private Mono<Void> resolveLoad(UUID truckId, Load load) {

        var truck = truckPort.findTruckById(truckId);
        return null;
    }

    @Override
    public Mono<Void> unloadTruck(UUID truckId, UUID loadId) {
        return null;
    }

    @Override
    public Flux<Load> getLoadsByTruck(UUID truckId) {
        return null;
    }
}
