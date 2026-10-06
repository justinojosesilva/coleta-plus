package br.com.fiap.coleta_plus.controller;

import br.com.fiap.coleta_plus.dto.EsgIndicatorsDTO;
import br.com.fiap.coleta_plus.service.IndicatorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/indicadores-sustentabilidade")
public class IndicatorsController {

    @Autowired
    private IndicatorsService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','AUDIT')")
    public ResponseEntity getIndicators() {
        try {
            return ResponseEntity.ok(service.getIndicators());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
