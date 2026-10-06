package br.com.fiap.coleta_plus.dto;

import br.com.fiap.coleta_plus.model.Collection;

import java.time.LocalDate;

public record CollectionResponseDTO(
        Long collectId,
        Long residueId,
        Integer qtyCollected,
        LocalDate dtCollection,
        String destination
) {
    public CollectionResponseDTO(Collection c) {
        this(
                c.getCollectId(),
                c.getResidue() != null ? c.getResidue().getResidueId() : null,
                c.getQtyCollected(),
                c.getDtCollection(),
                c.getDestination()
        );
    }
}
