package br.com.fiap.coleta_plus.controller;

import br.com.fiap.coleta_plus.dto.CreateResidueDTO;
import br.com.fiap.coleta_plus.dto.ResidueResponseDTO;
import br.com.fiap.coleta_plus.dto.UpdateResidueDTO;
import br.com.fiap.coleta_plus.service.ResidueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/residues")
public class ResidueController {

    @Autowired
    private ResidueService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity save(@Valid @RequestBody CreateResidueDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER','AUDIT')")
    public Page<ResidueResponseDTO> findAll(
            @RequestParam(required = false) Character recyclable,
            Pageable pageable) {
        return service.findAll(recyclable, pageable);
    }

    @GetMapping("/critical")
    @PreAuthorize("hasAnyRole('ADMIN','USER','AUDIT')")
    public ResponseEntity findCritical() {
        try {
            return ResponseEntity.ok(service.findCritical());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody UpdateResidueDTO dto) {
        try {
            return ResponseEntity.ok(service.update(id, dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
