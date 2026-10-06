package br.com.fiap.coleta_plus.service;

import br.com.fiap.coleta_plus.dto.CreateTypeResidueDTO;
import br.com.fiap.coleta_plus.dto.TypeResidueResponseDTO;
import br.com.fiap.coleta_plus.model.TypeResidue;
import br.com.fiap.coleta_plus.repository.TypeResidueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TypeResidueService {

    @Autowired
    private TypeResidueRepository repository;

    public TypeResidueResponseDTO save(CreateTypeResidueDTO dto) {
        TypeResidue type = new TypeResidue();
        BeanUtils.copyProperties(dto, type);
        TypeResidue saved = repository.save(type);
        log.info("Created type-residue id={} description={}", saved.getTypeId(), saved.getDescription());
        return new TypeResidueResponseDTO(saved);
    }

    public Page<TypeResidueResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(TypeResidueResponseDTO::new);
    }
}
