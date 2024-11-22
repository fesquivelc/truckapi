package com.test.truckapi.infrastructure.web.controllers;

import com.test.truckapi.domain.ports.input.LoadServicePort;
import com.test.truckapi.domain.ports.input.TruckServicePort;
import com.test.truckapi.infrastructure.web.dto.*;
import com.test.truckapi.infrastructure.web.mapper.LoadDtoMapper;
import com.test.truckapi.infrastructure.web.mapper.TruckDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/trucks")
@RequiredArgsConstructor
@Tag(name = "Trucks", description = "API to manage trucks and loads")
public class TruckController {

    private final TruckServicePort truckService;
    private final LoadServicePort loadServicePort;
    private final TruckDtoMapper truckDtoMapper;
    private final LoadDtoMapper loadDtoMapper;

    @Operation(summary = "Create a new truck")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Camión creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TruckResponseDTO> registerTruck(@Valid @RequestBody CreateTruckRequestDTO request) {
        log.debug("Registrando nuevo camión: {}", request);
        return Mono.just(request)
                .map(truckDtoMapper::toDomain)
                .flatMap(truckService::registerTruck)
                .map(truckDtoMapper::toDto)
                .onErrorResume(e ->
                        Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage()))
                );
    }

    @Operation(summary = "Get all trucks")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<TruckResponseDTO> getAllTrucks() {
        log.debug("Obteniendo todos los camiones");
        return truckService.getAllTrucks()
                .map(truckDtoMapper::toDto);
    }

    @Operation(summary = "Get truck by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Camión encontrado"),
            @ApiResponse(responseCode = "404", description = "Camión no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TruckResponseDTO> getTruckById(
            @Parameter(description = "ID del camión") @PathVariable String id) {
        log.debug("Obteniendo camión con ID: {}", id);
        return truckService.getTruckById(UUID.fromString(id))
                .map(truckDtoMapper::toDto)
                .onErrorMap(e -> new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e));
    }

    @Operation(summary = "Load a truck")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Truck not found"),
            @ApiResponse(responseCode = "400", description = "Some data is invalid")
    })
    @PostMapping(value = "/{id}/load", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<LoadResponseDTO> loadTruck(
            @Parameter(description = "ID del camión") @PathVariable String id,
            @Valid @RequestBody LoadRequestDTO request) {
        log.debug("Cargando camión {}: {}", id, request);
        return Mono.just(request)
                .map(dto -> loadDtoMapper.toDomain(dto, UUID.fromString(id)))
                .flatMap(loadServicePort::loadTruck)
                .map(loadDtoMapper::toDto);
    }

    //
//    @Operation(summary = "Descargar un camión")
//    @PostMapping(value = "/{id}/unload", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Mono<TruckResponseDTO> unloadTruck(
//            @Parameter(description = "ID del camión") @PathVariable String id) {
//        log.debug("Descargando camión: {}", id);
//        return truckService.unloadTruck(UUID.fromString(id))
//                .map(dtoMapper::toDto);
//    }
//
    @Operation(summary = "Get the loads of a truck")
    @GetMapping(value = "/{id}/loads", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<LoadResponseDTO> getTruckLoads(
            @Parameter(description = "ID del camión") @PathVariable String id) {
        log.debug("Obteniendo historial de cargas del camión: {}", id);
        return loadServicePort.getLoadsByTruck(UUID.fromString(id))
                .map(loadDtoMapper::toDto);
    }

    @Operation(summary = "Eliminar un camión")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Camión eliminado"),
            @ApiResponse(responseCode = "404", description = "Camión no encontrado"),
            @ApiResponse(responseCode = "400", description = "No se puede eliminar el camión")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteTruck(
            @Parameter(description = "ID del camión") @PathVariable String id) {
        log.debug("Eliminando camión: {}", id);
        return truckService
                .deleteTruck(UUID.fromString(id))
                .onErrorMap(e -> new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e));
    }
}
