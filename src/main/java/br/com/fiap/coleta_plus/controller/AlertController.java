package br.com.fiap.coleta_plus.controller;

import br.com.fiap.coleta_plus.dto.AlertResponseDTO;
import br.com.fiap.coleta_plus.dto.CreateAlertDTO;
import br.com.fiap.coleta_plus.service.AlertService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    @Autowired
    private AlertService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity save(@Valid @RequestBody CreateAlertDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/manual")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity saveManual(@Valid @RequestBody CreateAlertDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.saveManual(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER','AUDIT')")
    public Page<AlertResponseDTO> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }
}
