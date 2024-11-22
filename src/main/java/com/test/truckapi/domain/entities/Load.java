package com.test.truckapi.domain.entities;

import com.test.truckapi.domain.enums.LoadStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Load {
    private UUID id;
    private UUID truckId;
    private double volume;
    private String description;
    private LocalDateTime loadTimestamp;
    private LocalDateTime unloadTimestamp;
    private Long version;

    public LoadStatus getStatus() {
        if (unloadTimestamp == null) {
            return LoadStatus.LOADED;
        }
        return LoadStatus.UNLOADED;
    }
}
