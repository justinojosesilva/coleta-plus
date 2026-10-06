package br.com.fiap.coleta_plus.service;

import br.com.fiap.coleta_plus.dto.AlertResponseDTO;
import br.com.fiap.coleta_plus.dto.CreateAlertDTO;
import br.com.fiap.coleta_plus.model.Alert;
import br.com.fiap.coleta_plus.model.Residue;
import br.com.fiap.coleta_plus.repository.AlertRepository;
import br.com.fiap.coleta_plus.repository.ResidueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class AlertService {

    private static final double CAPACITY_ALERT_THRESHOLD = 0.9;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private ResidueRepository residueRepository;

    public AlertResponseDTO save(CreateAlertDTO dto) {
        Alert alert = buildAlert(
                residueRepository.getReferenceById(dto.residueId()),
                dto.type(),
                dto.message(),
                dto.dtAlert() != null ? dto.dtAlert() : LocalDate.now()
        );
        Alert saved = alertRepository.save(alert);
        log.info("Created alert id={} type={} residueId={}", saved.getAlertId(), saved.getType(), dto.residueId());
        return new AlertResponseDTO(saved);
    }

    public AlertResponseDTO saveManual(CreateAlertDTO dto) {
        Alert alert = buildAlert(
                residueRepository.getReferenceById(dto.residueId()),
                "MANUAL",
                dto.message(),
                dto.dtAlert() != null ? dto.dtAlert() : LocalDate.now()
        );
        Alert saved = alertRepository.save(alert);
        log.info("Created MANUAL alert id={} residueId={}", saved.getAlertId(), dto.residueId());
        return new AlertResponseDTO(saved);
    }

    public Page<AlertResponseDTO> findAll(Pageable pageable) {
        return alertRepository.findAll(pageable).map(AlertResponseDTO::new);
    }

    public void maybeGenerateCapacityAlert(Residue residue) {
        if (residue.getPoint() == null || residue.getPoint().getMaxCapacity() == null
                || residue.getQtyCurrent() == null) {
            return;
        }
        double threshold = CAPACITY_ALERT_THRESHOLD * residue.getPoint().getMaxCapacity();
        if (residue.getQtyCurrent() >= threshold) {
            Alert alert = buildAlert(residue, "CAPACITY",
                    "Residue " + residue.getResidueId() + " reached " + residue.getQtyCurrent()
                            + " of " + residue.getPoint().getMaxCapacity() + " capacity",
                    LocalDate.now());
            alertRepository.save(alert);
            log.warn("Auto-generated CAPACITY alert for residueId={} qty={}",
                    residue.getResidueId(), residue.getQtyCurrent());
        }
    }

    private Alert buildAlert(Residue residue, String type, String message, LocalDate dtAlert) {
        Alert alert = new Alert();
        alert.setResidue(residue);
        alert.setType(type);
        alert.setMessage(message);
        alert.setDtAlert(dtAlert);
        return alert;
    }
}
