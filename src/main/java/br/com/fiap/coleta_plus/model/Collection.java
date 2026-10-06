package br.com.fiap.coleta_plus.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "tb_collections")
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COLLECTIONS")
    @SequenceGenerator(name = "SEQ_COLLECTIONS", sequenceName = "SEQ_COLLECTIONS", allocationSize = 1)
    @Column(name = "collect_id")
    private Long collectId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "residue_id")
    private Residue residue;

    @Column(name = "qty_collected")
    private Integer qtyCollected;

    @Column(name = "dt_collection")
    private LocalDate dtCollection;

    @Column(name = "ds_destination")
    private String destination;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDate createdAt;

}
