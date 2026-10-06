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
@Table(name = "tb_alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ALERTS")
    @SequenceGenerator(name = "SEQ_ALERTS", sequenceName = "SEQ_ALERTS", allocationSize = 1)
    @Column(name = "alert_id")
    private Long alertId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "residue_id")
    private Residue residue;

    @Column(name = "tp_alert")
    private String type;

    @Column(name = "ds_message")
    private String message;

    @Column(name = "dt_alert")
    private LocalDate dtAlert;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDate createdAt;

}
