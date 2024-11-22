package com.test.truckapi.infrastructure.persistence.repositories;

import com.test.truckapi.infrastructure.persistence.entities.TruckEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface TruckReactiveRepository extends ReactiveCrudRepository<TruckEntity, UUID> {
    @Query("SELECT t.* FROM trucks t WHERE t.id = :truckId AND t.is_deleted = FALSE")
    Mono<TruckEntity> findByIdAndIsDeletedFalse(UUID truckId);

    @Query("SELECT t.* FROM trucks t WHERE t.is_deleted = FALSE")
    Flux<TruckEntity> findAllByIsDeletedFalse();

    @Query("UPDATE trucks SET is_deleted = TRUE WHERE id = :id")
    Mono<Void> softDeleteById(UUID id);
}
