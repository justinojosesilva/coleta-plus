package br.com.fiap.coleta_plus.controller;

import br.com.fiap.coleta_plus.dto.CreateUserDTO;
import br.com.fiap.coleta_plus.dto.LoginRequestDTO;
import br.com.fiap.coleta_plus.service.AuthService;
import br.com.fiap.coleta_plus.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequestDTO dto) {
        try {
            return ResponseEntity.ok(authService.login(dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody CreateUserDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
