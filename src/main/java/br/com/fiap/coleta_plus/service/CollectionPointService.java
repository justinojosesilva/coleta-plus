package br.com.fiap.coleta_plus.service;

import br.com.fiap.coleta_plus.dto.CollectionPointResponseDTO;
import br.com.fiap.coleta_plus.dto.CreateCollectionPointDTO;
import br.com.fiap.coleta_plus.model.CollectionPoint;
import br.com.fiap.coleta_plus.repository.CollectionPointRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CollectionPointService {

    @Autowired
    private CollectionPointRepository repository;

    public CollectionPointResponseDTO save(CreateCollectionPointDTO dto) {
        CollectionPoint point = new CollectionPoint();
        BeanUtils.copyProperties(dto, point);
        CollectionPoint saved = repository.save(point);
        log.info("Created collection point id={} name={}", saved.getPointId(), saved.getName());
        return new CollectionPointResponseDTO(saved);
    }

    public Page<CollectionPointResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(CollectionPointResponseDTO::new);
    }
}
