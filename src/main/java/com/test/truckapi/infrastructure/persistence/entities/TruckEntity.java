package com.test.truckapi.infrastructure.persistence.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;
import java.util.UUID;

@Data
@Table("trucks")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TruckEntity implements Persistable<UUID> {
    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    @Column("license_plate")
    private String licensePlate;
    @Column
    private String model;
    @Column("capacity_limit")
    private Double capacityLimit;
    @Column("current_load")
    private Double currentLoad;
    @Column
    private String status;
    @Version
    @Column
    private Long version;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
