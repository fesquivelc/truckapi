package com.test.truckapi.application.services;

import com.test.truckapi.domain.entities.Load;
import com.test.truckapi.domain.entities.Notification;
import com.test.truckapi.domain.entities.Truck;
import com.test.truckapi.domain.enums.TruckStatus;
import com.test.truckapi.domain.exceptions.*;
import com.test.truckapi.domain.ports.output.LoadPort;
import com.test.truckapi.domain.ports.output.NotificationPort;
import com.test.truckapi.domain.ports.output.TruckPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoadServiceTest {

    @Mock
    private TruckPort truckPort;

    @Mock
    private LoadPort loadPort;

    @Mock
    private NotificationPort notificationPort;

    @InjectMocks
    private LoadService loadService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void close() throws Exception {
        closeable.close();
    }

    @Test
    void loadTruck_Success() {
        Truck truck = new Truck();
        truck.setId(UUID.randomUUID());
        truck.setStatus(TruckStatus.AVAILABLE);
        truck.setCurrentLoad(0.0);
        truck.setCapacityLimit(100.0);

        Load load = new Load();
        load.setTruckId(truck.getId());
        load.setVolume(50);

        when(truckPort.findTruckById(truck.getId())).thenReturn(Mono.just(truck));
        when(truckPort.saveTruck(any(Truck.class))).thenReturn(Mono.just(truck));
        when(loadPort.saveLoad(any(Load.class))).thenReturn(Mono.just(load));

        Mono<Load> result = loadService.loadTruck(load);

        StepVerifier.create(result)
                .expectNext(load)
                .verifyComplete();

        verify(truckPort, times(1)).findTruckById(truck.getId());
        verify(truckPort, times(1)).saveTruck(any(Truck.class));
        verify(loadPort, times(1)).saveLoad(any(Load.class));
    }

    @Test
    void loadTruck_TruckNotFound() {
        UUID truckId = UUID.randomUUID();
        Load load = new Load();
        load.setTruckId(truckId);

        when(truckPort.findTruckById(truckId)).thenReturn(Mono.empty());

        Mono<Load> result = loadService.loadTruck(load);

        StepVerifier.create(result)
                .expectError(NotFoundException.class)
                .verify();

        verify(truckPort, times(1)).findTruckById(truckId);
        verify(truckPort, never()).saveTruck(any(Truck.class));
        verify(loadPort, never()).saveLoad(any(Load.class));
    }

    @Test
    void loadTruck_TruckCapacityException_ExceedsCapacity() {
        Truck truck = new Truck();
        truck.setId(UUID.randomUUID());
        truck.setStatus(TruckStatus.AVAILABLE);
        truck.setCurrentLoad(50.0);
        truck.setCapacityLimit(100.0);

        Load load = new Load();
        load.setTruckId(truck.getId());
        load.setVolume(60);

        when(truckPort.findTruckById(truck.getId())).thenReturn(Mono.just(truck));

        Mono<Load> result = loadService.loadTruck(load);

        StepVerifier.create(result)
                .expectError(TruckCapacityException.class)
                .verify();

        verify(truckPort, times(1)).findTruckById(truck.getId());
        verify(truckPort, never()).saveTruck(any(Truck.class));
        verify(loadPort, never()).saveLoad(any(Load.class));
    }

    @Test
    void loadTruck_TruckCapacityException_Loaded() {
        Truck truck = new Truck();
        truck.setId(UUID.randomUUID());
        truck.setStatus(TruckStatus.LOADED);
        truck.setCurrentLoad(100.0);
        truck.setCapacityLimit(100.0);

        Load load = new Load();
        load.setTruckId(truck.getId());
        load.setVolume(60);

        when(truckPort.findTruckById(truck.getId())).thenReturn(Mono.just(truck));

        Mono<Load> result = loadService.loadTruck(load);

        StepVerifier.create(result)
                .expectError(TruckCapacityException.class)
                .verify();

        verify(truckPort, times(1)).findTruckById(truck.getId());
        verify(truckPort, never()).saveTruck(any(Truck.class));
        verify(loadPort, never()).saveLoad(any(Load.class));
    }

    @Test
    void unloadTruck_Success() {
        Truck truck = new Truck();
        truck.setId(UUID.randomUUID());
        truck.setStatus(TruckStatus.LOADED);
        truck.setCurrentLoad(50.0);

        Load load = new Load();
        load.setId(UUID.randomUUID());
        load.setTruckId(truck.getId());
        load.setVolume(50);

        when(truckPort.findTruckById(truck.getId())).thenReturn(Mono.just(truck));
        when(loadPort.findLoadById(load.getId())).thenReturn(Mono.just(load));
        when(truckPort.saveTruck(any(Truck.class))).thenReturn(Mono.just(truck));
        when(loadPort.saveLoad(any(Load.class))).thenReturn(Mono.just(load));
        when(notificationPort.sendNotification(any(Notification.class))).thenReturn(Mono.empty());

        Mono<Void> result = loadService.unloadTruck(truck.getId(), load.getId());

        StepVerifier.create(result)
                .verifyComplete();

        verify(truckPort, times(1)).findTruckById(truck.getId());
        verify(loadPort, times(1)).findLoadById(load.getId());
        verify(truckPort, times(1)).saveTruck(any(Truck.class));
        verify(loadPort, times(1)).saveLoad(any(Load.class));
        verify(notificationPort, times(1)).sendNotification(any(Notification.class));
    }

    @Test
    void unloadTruck_LoadAlreadyUnloadedException() {
        Truck truck = new Truck();
        truck.setId(UUID.randomUUID());
        truck.setStatus(TruckStatus.LOADED);
        truck.setCurrentLoad(50.0);

        Load load = new Load();
        load.setId(UUID.randomUUID());
        load.setTruckId(truck.getId());
        load.setVolume(50);
        load.setUnloadTimestamp(LocalDateTime.now());

        when(truckPort.findTruckById(truck.getId())).thenReturn(Mono.just(truck));
        when(loadPort.findLoadById(load.getId())).thenReturn(Mono.just(load));

        Mono<Void> result = loadService.unloadTruck(truck.getId(), load.getId());

        StepVerifier.create(result)
                .expectError(LoadAlreadyUnloadedException.class)
                .verify();

        verify(truckPort, times(1)).findTruckById(truck.getId());
        verify(loadPort, times(1)).findLoadById(load.getId());
        verify(truckPort, never()).saveTruck(any(Truck.class));
        verify(loadPort, never()).saveLoad(any(Load.class));
        verify(notificationPort, never()).sendNotification(any(Notification.class));
    }

    @Test
    void getLoadsByTruck_Success() {
        UUID truckId = UUID.randomUUID();
        Load load1 = new Load();
        Load load2 = new Load();

        when(loadPort.finLoadsByTruckId(truckId)).thenReturn(Flux.just(load1, load2));

        Flux<Load> result = loadService.getLoadsByTruck(truckId);

        StepVerifier.create(result)
                .expectNext(load1, load2)
                .verifyComplete();

        verify(loadPort, times(1)).finLoadsByTruckId(truckId);
    }
}