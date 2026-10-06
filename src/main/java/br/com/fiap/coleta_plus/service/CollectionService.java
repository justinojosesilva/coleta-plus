package br.com.fiap.coleta_plus.service;

import br.com.fiap.coleta_plus.dto.CollectionResponseDTO;
import br.com.fiap.coleta_plus.dto.CreateCollectionDTO;
import br.com.fiap.coleta_plus.model.Collection;
import br.com.fiap.coleta_plus.model.Residue;
import br.com.fiap.coleta_plus.repository.CollectionRepository;
import br.com.fiap.coleta_plus.repository.ResidueRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class CollectionService {

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private ResidueRepository residueRepository;

    @Autowired
    private AlertService alertService;

    @Transactional
    public CollectionResponseDTO save(CreateCollectionDTO dto) {
        Residue residue = residueRepository.findById(dto.residueId())
                .orElseThrow(() -> new EntityNotFoundException("Residue " + dto.residueId() + " not found"));

        if (dto.qtyCollected() > residue.getQtyCurrent()) {
            throw new IllegalArgumentException(
                    "qtyCollected (" + dto.qtyCollected() + ") exceeds current stock (" + residue.getQtyCurrent() + ")");
        }

        residue.setQtyCurrent(residue.getQtyCurrent() - dto.qtyCollected());
        residue.setUpdatedAt(LocalDate.now());
        residueRepository.save(residue);

        Collection collection = new Collection();
        collection.setResidue(residue);
        collection.setQtyCollected(dto.qtyCollected());
        collection.setDtCollection(dto.dtCollection());
        collection.setDestination(dto.destination());
        Collection saved = collectionRepository.save(collection);
        log.info("Created collection id={} residueId={} qty={}", saved.getCollectId(), dto.residueId(), dto.qtyCollected());

        alertService.maybeGenerateCapacityAlert(residue);
        return new CollectionResponseDTO(saved);
    }

    public Page<CollectionResponseDTO> findAll(Pageable pageable) {
        return collectionRepository.findAll(pageable).map(CollectionResponseDTO::new);
    }

    public Page<CollectionResponseDTO> findByPoint(Long pointId, Pageable pageable) {
        return collectionRepository.findByResidue_Point_PointId(pointId, pageable).map(CollectionResponseDTO::new);
    }
}
