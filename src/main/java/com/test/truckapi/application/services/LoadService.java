package com.test.truckapi.application.services;

import com.test.truckapi.domain.entities.Load;
import com.test.truckapi.domain.entities.Notification;
import com.test.truckapi.domain.enums.TruckStatus;
import com.test.truckapi.domain.exceptions.LoadAlreadyUnloadedException;
import com.test.truckapi.domain.exceptions.TruckCapacityException;
import com.test.truckapi.domain.exceptions.MaxRetriesExceededException;
import com.test.truckapi.domain.ports.input.LoadServicePort;
import com.test.truckapi.domain.ports.output.LoadPort;
import com.test.truckapi.domain.ports.output.NotificationPort;
import com.test.truckapi.domain.ports.output.TruckPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.test.truckapi.application.utils.ExceptionUtils.buildNotFoundLoadException;
import static com.test.truckapi.application.utils.ExceptionUtils.buildNotFoundTruckException;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoadService implements LoadServicePort {
    private final TruckPort truckPort;
    private final LoadPort loadPort;
    private final NotificationPort notificationPort;

    @Override
    public Mono<Load> loadTruck(Load load) {
        return Mono.defer(() -> resolveLoad(load.getTruckId(), load))
                .retryWhen(retryRules());
    }

    private RetryBackoffSpec retryRules() {
        return Retry.backoff(3, Duration.ofMillis(50))
                .filter(e -> e instanceof OptimisticLockingFailureException)
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                    log.error("[MaxRetries] Max retries exceeded: {0}", retrySignal.failure());
                    throw new MaxRetriesExceededException();
                });

    }

    private Mono<Load> resolveLoad(UUID truckId, Load load) {
        return truckPort.findTruckById(truckId)
                .switchIfEmpty(buildNotFoundTruckException(truckId))
                .flatMap(truck -> {
                    if (truck.getStatus() == TruckStatus.LOADED) {
                        return Mono.error(new TruckCapacityException("The truck has full loaded"));
                    }
                    var newCurrentLoad = truck.getCurrentLoad() + load.getVolume();
                    if (newCurrentLoad > truck.getCapacityLimit()) {
                        return Mono.error(new TruckCapacityException("The load exceeds the current truck capacity"));
                    }
                    load.setTruckId(truckId);
                    truck.setCurrentLoad(newCurrentLoad);
                    truck.setStatus(newCurrentLoad == truck.getCapacityLimit() ? TruckStatus.LOADED : TruckStatus.AVAILABLE);
                    return truckPort.updateTruck(truck).then(loadPort.saveLoad(load));
                });
    }

    @Override
    public Mono<Void> unloadTruck(UUID truckId, UUID loadId) {
        return Mono.defer(() -> resolveUnload(truckId, loadId))
                .retryWhen(retryRules());
    }

    private Mono<Void> resolveUnload(UUID truckId, UUID loadId) {
        var truckJob = truckPort.findTruckById(truckId)
                .switchIfEmpty(buildNotFoundTruckException(truckId));
        var loadJob = loadPort.findLoadById(loadId)
                .switchIfEmpty(buildNotFoundLoadException(loadId));
        return Mono.zip(truckJob, loadJob)
                .flatMap(tuple -> {
                    var truck = tuple.getT1();
                    var load = tuple.getT2();
                    if (load.getUnloadTimestamp() != null) {
                        return Mono.error(new LoadAlreadyUnloadedException("The load is already unloaded"));
                    }
                    if (truck.getStatus() == TruckStatus.UNLOADED) {
                        return Mono.error(new TruckCapacityException("The truck not have loads"));
                    }
                    var newCurrentLoad = truck.getCurrentLoad() - load.getVolume();
                    truck.setCurrentLoad(newCurrentLoad);
                    truck.setStatus(newCurrentLoad == 0 ? TruckStatus.UNLOADED : TruckStatus.AVAILABLE);
                    load.setUnloadTimestamp(LocalDateTime.now());
                    return truckPort.saveTruck(truck)
                            .then(loadPort.saveLoad(load))
                            .then(notificationPort.sendNotification(buildUnloadNotification(load)));
                });
    }

    private Notification<Load> buildUnloadNotification(Load load) {
        return Notification.<Load>builder()
                .type("UNLOAD")
                .data(load)
                .build();
    }

    @Override
    public Flux<Load> getLoadsByTruck(UUID truckId) {
        return loadPort.finLoadsByTruckId(truckId);
    }
}
