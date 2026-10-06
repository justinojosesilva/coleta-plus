package br.com.fiap.coleta_plus.service;

import br.com.fiap.coleta_plus.dto.CollectionResponseDTO;
import br.com.fiap.coleta_plus.dto.CreateCollectionDTO;
import br.com.fiap.coleta_plus.model.Collection;
import br.com.fiap.coleta_plus.model.Residue;
import br.com.fiap.coleta_plus.repository.CollectionRepository;
import br.com.fiap.coleta_plus.repository.ResidueRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CollectionServiceTest {

    @Mock
    private CollectionRepository collectionRepository;

    @Mock
    private ResidueRepository residueRepository;

    @Mock
    private AlertService alertService;

    @InjectMocks
    private CollectionService service;

    @Test
    void registraColetaEAbateDoEstoqueDoResiduo() {
        Residue residue = new Residue();
        residue.setResidueId(1L);
        residue.setQtyCurrent(100);
        when(residueRepository.findById(1L)).thenReturn(Optional.of(residue));
        when(collectionRepository.save(any(Collection.class))).thenAnswer(inv -> {
            Collection c = inv.getArgument(0);
            c.setCollectId(10L);
            return c;
        });

        CollectionResponseDTO result = service.save(
                new CreateCollectionDTO(1L, 40, LocalDate.of(2026, 10, 1), "Cooperativa Recicla"));

        assertThat(residue.getQtyCurrent()).isEqualTo(60);
        assertThat(result.collectId()).isEqualTo(10L);
        assertThat(result.qtyCollected()).isEqualTo(40);
        verify(alertService).maybeGenerateCapacityAlert(residue);
    }

    @Test
    void rejeitaColetaMaiorQueOEstoque() {
        Residue residue = new Residue();
        residue.setResidueId(1L);
        residue.setQtyCurrent(10);
        when(residueRepository.findById(1L)).thenReturn(Optional.of(residue));

        assertThatThrownBy(() -> service.save(new CreateCollectionDTO(1L, 50, LocalDate.now(), null)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exceeds current stock");
        verify(collectionRepository, never()).save(any());
    }

    @Test
    void falhaQuandoResiduoNaoExiste() {
        when(residueRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.save(new CreateCollectionDTO(99L, 1, LocalDate.now(), null)))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
