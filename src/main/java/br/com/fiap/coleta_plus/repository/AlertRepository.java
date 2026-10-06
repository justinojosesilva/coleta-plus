package br.com.fiap.coleta_plus.repository;

import br.com.fiap.coleta_plus.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {
}
