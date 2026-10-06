package br.com.fiap.coleta_plus.dto;

import br.com.fiap.coleta_plus.model.UserRole;

public record AuthResponseDTO(
        String token,
        String email,
        UserRole role
) {
}
