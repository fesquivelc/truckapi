package com.test.truckapi.application.services;

import com.test.truckapi.domain.entities.Truck;
import com.test.truckapi.domain.enums.TruckStatus;
import com.test.truckapi.domain.exceptions.NotFoundException;
import com.test.truckapi.domain.exceptions.TruckNotUnloadedException;
import com.test.truckapi.domain.ports.output.TruckPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;


class TruckServiceTest {

    @Mock
    private TruckPort truckPort;

    @InjectMocks
    private TruckService truckService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = openMocks(this);
    }

    @AfterEach
    void close() throws Exception {
        closeable.close();
    }

    @Test
    void registerTruck() {
        Truck truck = new Truck();
        when(truckPort.saveTruck(any(Truck.class))).thenReturn(Mono.just(truck));

        Mono<Truck> result = truckService.registerTruck(truck);

        assertNotNull(result);
        assertEquals(truck, result.block());
        verify(truckPort, times(1)).saveTruck(truck);
    }

    @Test
    void deleteTruck_Success() {
        UUID truckId = UUID.randomUUID();
        Truck truck = new Truck();
        truck.setId(truckId);
        truck.setStatus(TruckStatus.UNLOADED);

        when(truckPort.findTruckById(truckId)).thenReturn(Mono.just(truck));
        when(truckPort.deleteTruck(truckId)).thenReturn(Mono.empty());

        Mono<Void> result = truckService.deleteTruck(truckId);

        assertNotNull(result);
        result.block();
        verify(truckPort, times(1)).findTruckById(truckId);
        verify(truckPort, times(1)).deleteTruck(truckId);
    }

    @Test
    void deleteTruck_TruckNotUnloadedException() {
        UUID truckId = UUID.randomUUID();
        Truck truck = new Truck();
        truck.setId(truckId);
        truck.setStatus(TruckStatus.LOADED);

        when(truckPort.findTruckById(truckId)).thenReturn(Mono.just(truck));

        Mono<Void> result = truckService.deleteTruck(truckId);

        assertThrows(TruckNotUnloadedException.class, result::block);
        verify(truckPort, times(1)).findTruckById(truckId);
        verify(truckPort, never()).deleteTruck(truckId);
    }

    @Test
    void deleteTruck_NotFound() {
        UUID truckId = UUID.randomUUID();

        when(truckPort.findTruckById(truckId)).thenReturn(Mono.empty());

        Mono<Void> result = truckService.deleteTruck(truckId);

        assertNotNull(result);
        assertThrows(NotFoundException.class, result::block); // Assuming buildNotFoundTruckException throws RuntimeException
        verify(truckPort, times(1)).findTruckById(truckId);
        verify(truckPort, never()).deleteTruck(truckId);
    }

    @Test
    void getAllTrucks() {
        Truck truck1 = new Truck();
        Truck truck2 = new Truck();

        when(truckPort.getAllTrucks()).thenReturn(Flux.just(truck1, truck2));

        Flux<Truck> result = truckService.getAllTrucks();

        assertNotNull(result);
        assertEquals(2, result.collectList().block().size());
        verify(truckPort, times(1)).getAllTrucks();
    }

    @Test
    void getTruckById_Success() {
        UUID truckId = UUID.randomUUID();
        Truck truck = new Truck();
        truck.setId(truckId);

        when(truckPort.findTruckById(truckId)).thenReturn(Mono.just(truck));

        Mono<Truck> result = truckService.getTruckById(truckId);

        assertNotNull(result);
        assertEquals(truck, result.block());
        verify(truckPort, times(1)).findTruckById(truckId);
    }

    @Test
    void getTruckById_NotFound() {
        UUID truckId = UUID.randomUUID();

        when(truckPort.findTruckById(truckId)).thenReturn(Mono.empty());

        Mono<Truck> result = truckService.getTruckById(truckId);

        assertNotNull(result);
        assertThrows(NotFoundException.class, result::block); // Assuming buildNotFoundTruckException throws RuntimeException
        verify(truckPort, times(1)).findTruckById(truckId);
    }
}
