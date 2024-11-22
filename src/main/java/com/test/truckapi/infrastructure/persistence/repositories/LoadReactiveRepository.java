package com.test.truckapi.infrastructure.persistence.repositories;

import com.test.truckapi.infrastructure.persistence.entities.LoadEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface LoadReactiveRepository extends R2dbcRepository<LoadEntity, UUID> {
    @Query("SELECT l.* FROM loads l INNER JOIN trucks t ON l.truck_id = t.id WHERE t.id = :truckId AND t.is_deleted = FALSE")
    Flux<LoadEntity> findByTruckId(UUID truckId);
}
