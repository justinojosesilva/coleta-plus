package br.com.fiap.coleta_plus.controller;

import br.com.fiap.coleta_plus.dto.CreateUserDTO;
import br.com.fiap.coleta_plus.dto.UserResponseDTO;
import br.com.fiap.coleta_plus.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity save(@Valid @RequestBody CreateUserDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserResponseDTO> findAll(Pageable pageable) {
        return userService.findAll(pageable);
    }
}
