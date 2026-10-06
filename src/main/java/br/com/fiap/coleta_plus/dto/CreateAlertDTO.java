package br.com.fiap.coleta_plus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateAlertDTO(
        @NotNull Long residueId,
        @NotBlank String type,
        @NotBlank String message,
        LocalDate dtAlert
) {
}
