package com.test.truckapi.domain.ports.output;

import com.test.truckapi.domain.entities.Load;
import reactor.core.publisher.Mono;

public interface LoadPort {
    Mono<Load> findLoadById(String loadId);
}
