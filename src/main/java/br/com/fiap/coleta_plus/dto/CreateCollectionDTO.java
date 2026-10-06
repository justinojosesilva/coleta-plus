package br.com.fiap.coleta_plus.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record CreateCollectionDTO(
        @NotNull Long residueId,
        @NotNull @Positive Integer qtyCollected,
        @NotNull LocalDate dtCollection,
        String destination
) {
}
