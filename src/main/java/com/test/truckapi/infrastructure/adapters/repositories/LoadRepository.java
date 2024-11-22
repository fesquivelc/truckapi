package com.test.truckapi.infrastructure.adapters.repositories;

import com.test.truckapi.domain.entities.Load;
import com.test.truckapi.domain.ports.output.LoadPort;
import com.test.truckapi.infrastructure.adapters.mappers.LoadMapper;
import com.test.truckapi.infrastructure.persistence.repositories.LoadReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LoadRepository implements LoadPort {
    private final LoadMapper loadMapper;
    private final LoadReactiveRepository loadReactiveRepository;

    @Override
    public Mono<Load> findLoadById(UUID loadId) {
        return loadReactiveRepository.findById(loadId)
                .map(loadMapper::toDomain);
    }

    @Override
    public Mono<Load> saveLoad(Load load) {
        return Mono.just(load)
                .map(loadMapper::toEntity)
                .flatMap(loadReactiveRepository::save)
                .map(loadMapper::toDomain);
    }

    @Override
    public Flux<Load> finLoadsByTruckId(UUID truckId) {
        return loadReactiveRepository.findByTruckId(truckId)
                .map(loadMapper::toDomain);
    }
}
