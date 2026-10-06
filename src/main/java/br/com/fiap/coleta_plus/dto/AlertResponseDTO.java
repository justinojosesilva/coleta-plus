package br.com.fiap.coleta_plus.dto;

import br.com.fiap.coleta_plus.model.Alert;

import java.time.LocalDate;

public record AlertResponseDTO(
        Long alertId,
        Long residueId,
        String type,
        String message,
        LocalDate dtAlert
) {
    public AlertResponseDTO(Alert a) {
        this(
                a.getAlertId(),
                a.getResidue() != null ? a.getResidue().getResidueId() : null,
                a.getType(),
                a.getMessage(),
                a.getDtAlert()
        );
    }
}
