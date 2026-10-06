package br.com.fiap.coleta_plus.service;

import br.com.fiap.coleta_plus.dto.EsgIndicatorsDTO;
import br.com.fiap.coleta_plus.repository.CollectionPointRepository;
import br.com.fiap.coleta_plus.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndicatorsService {

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private CollectionPointRepository pointRepository;

    public EsgIndicatorsDTO getIndicators() {
        Long recycled = collectionRepository.sumQtyCollectedByRecyclable('Y');
        Long nonRecycled = collectionRepository.sumQtyCollectedByRecyclable('N');
        Long collected = collectionRepository.sumQtyCollected();
        Long currentStock = pointRepository.sumCurrentStock();

        double denominator = (double) (collected + currentStock);
        Double efficiency = denominator == 0.0 ? 0.0 : collected / denominator;

        return new EsgIndicatorsDTO(
                recycled,
                nonRecycled,
                pointRepository.findPointsOverCapacity(),
                efficiency
        );
    }
}
