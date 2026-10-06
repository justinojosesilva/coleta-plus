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
@Table(name = "tb_residue")
public class Residue {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RESIDUE")
    @SequenceGenerator(name = "SEQ_RESIDUE", sequenceName = "SEQ_RESIDUE", allocationSize = 1)
    @Column(name = "residue_id")
    private Long residueId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "point_id")
    private CollectionPoint point;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "type_id")
    private TypeResidue type;

    @Column(name = "qty_current")
    private Integer qtyCurrent;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "active")
    private Character active;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDate createdAt;

}
