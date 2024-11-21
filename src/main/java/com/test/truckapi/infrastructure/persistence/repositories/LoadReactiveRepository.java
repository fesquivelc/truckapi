package com.test.truckapi.infrastructure.persistence.repositories;

import com.test.truckapi.infrastructure.persistence.entities.LoadEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LoadReactiveRepository extends R2dbcRepository<LoadEntity, UUID> {
}
