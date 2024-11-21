package com.test.truckapi.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTruckRequestDTO {
    @NotBlank(message = "La matrícula es obligatoria")
    @Schema(description = "La matrícula del camión", example = "ABC123")
    @Size(max = 20, message = "La matrícula no puede tener más de 20 caracteres")
    private String licensePlate;

    @NotBlank(message = "El modelo es obligatorio")
    @Schema(description = "El modelo del camión", example = "Volvo FH16")
    @Size(max = 100, message = "El modelo no puede tener más de 100 caracteres")
    private String model;

    @NotNull(message = "La capacidad maxima de carga es obligatoria")
    @Positive(message = "La capacidad maxima de carga debe ser mayor que 0")
    @Schema(description = "La capacidad maxima de carga del camión en metros cubicos", example = "40")
    private Double capacityLimit;
}
