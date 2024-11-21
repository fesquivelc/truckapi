package com.test.truckapi.infrastructure.persistence.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Table("loads")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LoadEntity {
    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    @Column
    private Double volume;
    @Column
    private String description;
    @Column("load_timestamp")
    private LocalDateTime loadTimestamp;
    @Column("unload_timestamp")
    private LocalDateTime unloadTimestamp;
    @Column("created_at")
    private LocalDateTime createdAt;
    @Column("updated_at")
    private LocalDateTime updatedAt;
    @Column("truck_id")
    private UUID truckId;
    @Version
    @Column
    private Long version;
}
