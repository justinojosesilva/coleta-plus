package br.com.fiap.coleta_plus.repository;

import br.com.fiap.coleta_plus.model.Residue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResidueRepository extends JpaRepository<Residue, Long> {

    Page<Residue> findByActive(Character active, Pageable pageable);

    @Query("""
            SELECT r FROM Residue r
            WHERE r.active = 'Y' AND r.type.recyclable = :recyclable
            """)
    Page<Residue> findByActiveAndRecyclable(@Param("recyclable") Character recyclable, Pageable pageable);

    @Query("""
            SELECT r FROM Residue r
            WHERE r.active = 'Y' AND r.qtyCurrent >= 0.8 * r.point.maxCapacity
            """)
    List<Residue> findCritical();
}
