package br.com.fiap.coleta_plus.repository;

import br.com.fiap.coleta_plus.model.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CollectionRepository extends JpaRepository<Collection, Long> {

    Page<Collection> findByResidue_Point_PointId(Long pointId, Pageable pageable);

    @Query("""
            SELECT COALESCE(SUM(c.qtyCollected), 0) FROM Collection c
            WHERE c.residue.type.recyclable = :recyclable
            """)
    Long sumQtyCollectedByRecyclable(@Param("recyclable") Character recyclable);

    @Query("SELECT COALESCE(SUM(c.qtyCollected), 0) FROM Collection c")
    Long sumQtyCollected();
}
