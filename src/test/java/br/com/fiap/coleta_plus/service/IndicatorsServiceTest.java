package br.com.fiap.coleta_plus.service;

import br.com.fiap.coleta_plus.dto.EsgIndicatorsDTO;
import br.com.fiap.coleta_plus.repository.CollectionPointRepository;
import br.com.fiap.coleta_plus.repository.CollectionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IndicatorsServiceTest {

    @Mock
    private CollectionRepository collectionRepository;

    @Mock
    private CollectionPointRepository pointRepository;

    @InjectMocks
    private IndicatorsService service;

    @Test
    void calculaEficienciaDeColetaComBaseNoEstoqueAtual() {
        when(collectionRepository.sumQtyCollectedByRecyclable('Y')).thenReturn(60L);
        when(collectionRepository.sumQtyCollectedByRecyclable('N')).thenReturn(15L);
        when(collectionRepository.sumQtyCollected()).thenReturn(75L);
        when(pointRepository.sumCurrentStock()).thenReturn(25L);
        when(pointRepository.findPointsOverCapacity()).thenReturn(List.of());

        EsgIndicatorsDTO dto = service.getIndicators();

        assertThat(dto.volumeRecycled()).isEqualTo(60L);
        assertThat(dto.volumeNonRecycled()).isEqualTo(15L);
        assertThat(dto.collectionEfficiency()).isEqualTo(0.75);
        assertThat(dto.pointsOverCapacity()).isEmpty();
    }

    @Test
    void eficienciaEhZeroQuandoNaoHaColetaNemEstoque() {
        when(collectionRepository.sumQtyCollectedByRecyclable('Y')).thenReturn(0L);
        when(collectionRepository.sumQtyCollectedByRecyclable('N')).thenReturn(0L);
        when(collectionRepository.sumQtyCollected()).thenReturn(0L);
        when(pointRepository.sumCurrentStock()).thenReturn(0L);
        when(pointRepository.findPointsOverCapacity()).thenReturn(List.of());

        assertThat(service.getIndicators().collectionEfficiency()).isZero();
    }
}
