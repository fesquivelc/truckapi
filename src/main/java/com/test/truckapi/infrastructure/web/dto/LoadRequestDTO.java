package com.test.truckapi.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoadRequestDTO {
    @NotNull
    @Schema(description = "The volume of the load in cubic meters", example = "2.5")
    private Double volume;
    @NotBlank(message = "The description is mandatory")
    @Schema(description = "The description of the load", example = "TV Samsung 50 inches")
    private String description;
}
