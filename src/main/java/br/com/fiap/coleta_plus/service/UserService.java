package br.com.fiap.coleta_plus.service;

import br.com.fiap.coleta_plus.dto.CreateUserDTO;
import br.com.fiap.coleta_plus.dto.UserResponseDTO;
import br.com.fiap.coleta_plus.model.User;
import br.com.fiap.coleta_plus.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDTO save(CreateUserDTO dto) {
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(dto.role());
        User saved = userRepository.save(user);
        log.info("Created user id={} role={}", saved.getUserId(), saved.getRole());
        return new UserResponseDTO(saved);
    }

    public Page<UserResponseDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserResponseDTO::new);
    }
}
