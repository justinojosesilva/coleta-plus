package br.com.fiap.coleta_plus.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "tb_collections_points")
public class CollectionPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COLLECTIONS_POINTS")
    @SequenceGenerator(name = "SEQ_COLLECTIONS_POINTS", sequenceName = "SEQ_COLLECTIONS_POINTS", allocationSize = 1)
    @Column(name = "point_id")
    private Long pointId;

    @Column(name = "nm_point")
    private String name;

    @Column(name = "ds_location")
    private String location;

    @Column(name = "mx_capacity")
    private Integer maxCapacity;

}
