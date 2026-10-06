package br.com.fiap.coleta_plus.controller;

import br.com.fiap.coleta_plus.dto.CreateTypeResidueDTO;
import br.com.fiap.coleta_plus.dto.ResidueResponseDTO;
import br.com.fiap.coleta_plus.dto.TypeResidueResponseDTO;
import br.com.fiap.coleta_plus.service.TypeResidueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/type-residues")
public class TypeResidueController {

    @Autowired
    private TypeResidueService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity save(@Valid @RequestBody CreateTypeResidueDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public Page<TypeResidueResponseDTO> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }
}
