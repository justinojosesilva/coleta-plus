package br.com.fiap.coleta_plus.service;

import br.com.fiap.coleta_plus.model.Alert;
import br.com.fiap.coleta_plus.model.CollectionPoint;
import br.com.fiap.coleta_plus.model.Residue;
import br.com.fiap.coleta_plus.repository.AlertRepository;
import br.com.fiap.coleta_plus.repository.ResidueRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AlertServiceTest {

    @Mock
    private AlertRepository alertRepository;

    @Mock
    private ResidueRepository residueRepository;

    @InjectMocks
    private AlertService service;

    private Residue residue(int qtyCurrent, int maxCapacity) {
        CollectionPoint point = new CollectionPoint();
        point.setMaxCapacity(maxCapacity);
        Residue residue = new Residue();
        residue.setResidueId(5L);
        residue.setPoint(point);
        residue.setQtyCurrent(qtyCurrent);
        return residue;
    }

    @Test
    void geraAlertaDeCapacidadeAoAtingir90PorCento() {
        service.maybeGenerateCapacityAlert(residue(90, 100));

        ArgumentCaptor<Alert> captor = ArgumentCaptor.forClass(Alert.class);
        verify(alertRepository).save(captor.capture());
        assertThat(captor.getValue().getType()).isEqualTo("CAPACITY");
    }

    @Test
    void naoGeraAlertaAbaixoDoLimite() {
        service.maybeGenerateCapacityAlert(residue(89, 100));

        verify(alertRepository, never()).save(any());
    }

    @Test
    void ignoraResiduoSemPontoDeColeta() {
        Residue residue = new Residue();
        residue.setQtyCurrent(1000);

        service.maybeGenerateCapacityAlert(residue);

        verify(alertRepository, never()).save(any());
    }
}
