package br.com.fiap.coleta_plus.dto;

import br.com.fiap.coleta_plus.model.Residue;

import java.time.LocalDate;

public record ResidueResponseDTO(
        Long residueId,
        Long pointId,
        Long typeId,
        Integer qtyCurrent,
        LocalDate updatedAt
) {
    public ResidueResponseDTO(Residue r) {
        this(
                r.getResidueId(),
                r.getPoint() != null ? r.getPoint().getPointId() : null,
                r.getType() != null ? r.getType().getTypeId() : null,
                r.getQtyCurrent(),
                r.getUpdatedAt()
        );
    }
}
