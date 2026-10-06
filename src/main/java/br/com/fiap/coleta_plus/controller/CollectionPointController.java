package br.com.fiap.coleta_plus.controller;

import br.com.fiap.coleta_plus.dto.CollectionPointResponseDTO;
import br.com.fiap.coleta_plus.dto.CreateCollectionPointDTO;
import br.com.fiap.coleta_plus.service.CollectionPointService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/collection-points")
public class CollectionPointController {

    @Autowired
    private CollectionPointService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity save(@Valid @RequestBody CreateCollectionPointDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public Page<CollectionPointResponseDTO> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }
}
