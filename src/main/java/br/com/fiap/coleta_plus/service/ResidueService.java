package br.com.fiap.coleta_plus.service;

import br.com.fiap.coleta_plus.dto.CreateResidueDTO;
import br.com.fiap.coleta_plus.dto.ResidueResponseDTO;
import br.com.fiap.coleta_plus.dto.UpdateResidueDTO;
import br.com.fiap.coleta_plus.model.Residue;
import br.com.fiap.coleta_plus.repository.CollectionPointRepository;
import br.com.fiap.coleta_plus.repository.ResidueRepository;
import br.com.fiap.coleta_plus.repository.TypeResidueRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class ResidueService {

    @Autowired
    private ResidueRepository residueRepository;

    @Autowired
    private CollectionPointRepository pointRepository;

    @Autowired
    private TypeResidueRepository typeRepository;

    @Autowired
    private AlertService alertService;

    @Transactional
    public ResidueResponseDTO save(CreateResidueDTO dto) {
        Residue residue = new Residue();
        residue.setPoint(pointRepository.getReferenceById(dto.pointId()));
        residue.setType(typeRepository.getReferenceById(dto.typeId()));
        residue.setQtyCurrent(dto.qtyCurrent());
        residue.setUpdatedAt(LocalDate.now());
        residue.setActive('Y');
        Residue saved = residueRepository.save(residue);
        log.info("Created residue id={} pointId={} typeId={}", saved.getResidueId(), dto.pointId(), dto.typeId());
        alertService.maybeGenerateCapacityAlert(saved);
        return new ResidueResponseDTO(saved);
    }

    @Transactional
    public ResidueResponseDTO update(Long id, UpdateResidueDTO dto) {
        Residue residue = residueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Residue " + id + " not found"));
        residue.setQtyCurrent(dto.qtyCurrent());
        residue.setUpdatedAt(LocalDate.now());
        Residue saved = residueRepository.save(residue);
        log.info("Updated residue id={} qtyCurrent={}", id, dto.qtyCurrent());
        alertService.maybeGenerateCapacityAlert(saved);
        return new ResidueResponseDTO(saved);
    }

    @Transactional
    public void delete(Long id) {
        Residue residue = residueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Residue " + id + " not found"));
        residue.setActive('N');
        residueRepository.save(residue);
        log.info("Soft-deleted residue id={}", id);
    }

    public Page<ResidueResponseDTO> findAll(Character recyclable, Pageable pageable) {
        Page<Residue> page = (recyclable == null)
                ? residueRepository.findByActive('Y', pageable)
                : residueRepository.findByActiveAndRecyclable(recyclable, pageable);
        return page.map(ResidueResponseDTO::new);
    }

    public List<ResidueResponseDTO> findCritical() {
        return residueRepository.findCritical().stream().map(ResidueResponseDTO::new).toList();
    }
}
