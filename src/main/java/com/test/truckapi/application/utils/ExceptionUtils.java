package com.test.truckapi.application.utils;

import com.test.truckapi.domain.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
public class ExceptionUtils {
    public static <T> Mono<T> buildNotFoundTruckException(UUID truckId) {
        return Mono.defer(() -> {
            log.error("[TruckNotFound] id={}", truckId);
            return Mono.error(
                    new NotFoundException(String.format("Truck with id=%s not found", truckId))
            );
        });
    }

    public static <T> Mono<T> buildNotFoundLoadException(UUID loadId) {
        return Mono.defer(() -> {
            log.error("[LoadNotFound] id={}", loadId);
            return Mono.error(
                    new NotFoundException(String.format("Load with id=%s not found", loadId))
            );
        });
    }
}
