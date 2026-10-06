package br.com.fiap.coleta_plus.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateResidueDTO(
        @NotNull Long pointId,
        @NotNull Long typeId,
        @NotNull @PositiveOrZero Integer qtyCurrent
) {
}
