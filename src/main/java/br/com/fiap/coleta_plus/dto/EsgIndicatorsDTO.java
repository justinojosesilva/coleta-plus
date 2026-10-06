package br.com.fiap.coleta_plus.dto;

import java.util.List;

public record EsgIndicatorsDTO(
        Long volumeRecycled,
        Long volumeNonRecycled,
        List<PointOverCapacityDTO> pointsOverCapacity,
        Double collectionEfficiency
) {
}
