package br.com.fiap.coleta_plus.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "tb_type_residue")
public class TypeResidue {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TYPE_RESIDUE")
    @SequenceGenerator(name = "SEQ_TYPE_RESIDUE", sequenceName = "SEQ_TYPE_RESIDUE", allocationSize = 1)
    @Column(name = "type_id")
    private Long typeId;

    @Column(name = "description")
    private String description;

    @Column(name = "recyclable")
    private Character recyclable;

}
