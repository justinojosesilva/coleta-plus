package br.com.fiap.coleta_plus.dto;

import br.com.fiap.coleta_plus.model.TypeResidue;

public record TypeResidueResponseDTO(
        Long typeId,
        String description,
        Character recyclable
) {
    public TypeResidueResponseDTO(TypeResidue t) {
        this(t.getTypeId(), t.getDescription(), t.getRecyclable());
    }
}
