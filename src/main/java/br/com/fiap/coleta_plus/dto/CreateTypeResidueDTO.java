package br.com.fiap.coleta_plus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTypeResidueDTO(
        @NotBlank String description,
        @NotNull Character recyclable
) {
}
