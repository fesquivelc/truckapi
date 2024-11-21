package com.test.truckapi.infrastructure.adapters.repositories;

import com.test.truckapi.domain.entities.Load;
import com.test.truckapi.domain.ports.output.LoadPort;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class LoadRepository implements LoadPort {

    @Override
    public Mono<Load> findLoadById(String loadId) {
        return Mono.empty();
    }
}
