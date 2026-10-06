package br.com.fiap.coleta_plus.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateResidueDTO(
        @NotNull @PositiveOrZero Integer qtyCurrent
) {
}
