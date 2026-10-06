package br.com.fiap.coleta_plus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateCollectionPointDTO(
        @NotBlank String name,
        @NotBlank String location,
        @NotNull @Positive Integer maxCapacity
) {
}
