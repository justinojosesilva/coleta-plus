package br.com.fiap.coleta_plus.repository;

import br.com.fiap.coleta_plus.dto.PointOverCapacityDTO;
import br.com.fiap.coleta_plus.model.CollectionPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CollectionPointRepository extends JpaRepository<CollectionPoint, Long> {

    @Query("""
            SELECT new br.com.fiap.coleta_plus.dto.PointOverCapacityDTO(
                p.pointId, p.name, p.maxCapacity, SUM(r.qtyCurrent))
            FROM CollectionPoint p JOIN Residue r ON r.point = p
            WHERE r.active = 'Y'
            GROUP BY p.pointId, p.name, p.maxCapacity
            HAVING SUM(r.qtyCurrent) > p.maxCapacity
            """)
    List<PointOverCapacityDTO> findPointsOverCapacity();

    @Query("""
            SELECT COALESCE(SUM(r.qtyCurrent), 0) FROM Residue r
            WHERE r.active = 'Y'
            """)
    Long sumCurrentStock();
}
