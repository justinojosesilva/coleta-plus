package br.com.fiap.coleta_plus.dto;

public record PointOverCapacityDTO(
        Long pointId,
        String name,
        Integer maxCapacity,
        Long currentStock
) {
}
