package br.com.fiap.coleta_plus.dto;

import br.com.fiap.coleta_plus.model.User;
import br.com.fiap.coleta_plus.model.UserRole;

public record UserResponseDTO(
        Long userId,
        String name,
        String email,
        UserRole role
) {
    public UserResponseDTO(User user) {
        this(user.getUserId(), user.getName(), user.getEmail(), user.getRole());
    }
}
