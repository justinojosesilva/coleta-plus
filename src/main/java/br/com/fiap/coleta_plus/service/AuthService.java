package br.com.fiap.coleta_plus.service;

import br.com.fiap.coleta_plus.dto.AuthResponseDTO;
import br.com.fiap.coleta_plus.dto.LoginRequestDTO;
import br.com.fiap.coleta_plus.model.User;
import br.com.fiap.coleta_plus.repository.UserRepository;
import br.com.fiap.coleta_plus.security.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    public AuthResponseDTO login(LoginRequestDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.email());
        String token = jwtService.generateToken(userDetails);
        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        log.info("User {} logged in", dto.email());
        return new AuthResponseDTO(token, user.getEmail(), user.getRole());
    }
}
