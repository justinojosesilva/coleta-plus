package br.com.fiap.coleta_plus.dto;

import br.com.fiap.coleta_plus.model.CollectionPoint;

public record CollectionPointResponseDTO(
        Long pointId,
        String name,
        String location,
        Integer maxCapacity
) {
    public CollectionPointResponseDTO(CollectionPoint p) {
        this(p.getPointId(), p.getName(), p.getLocation(), p.getMaxCapacity());
    }
}
