package br.com.fiap.coleta_plus.controller;

import br.com.fiap.coleta_plus.dto.CollectionResponseDTO;
import br.com.fiap.coleta_plus.dto.CreateCollectionDTO;
import br.com.fiap.coleta_plus.service.CollectionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/collections")
public class CollectionController {

    @Autowired
    private CollectionService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity save(@Valid @RequestBody CreateCollectionDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER','AUDIT')")
    public Page<CollectionResponseDTO> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/by-point/{pointId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER','AUDIT')")
    public Page<CollectionResponseDTO> findByPoint(@PathVariable Long pointId, Pageable pageable) {
        return service.findByPoint(pointId, pageable);
    }
}
