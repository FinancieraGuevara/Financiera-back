package com.financierag.financieraguevaraapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "serie_numeracion")
public class SerieNumeracion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ser_num_boleta")
    private Integer boleta;

    @Column(name = "ser_num_factura")
    private Integer factura;

    @ManyToOne
    @JoinColumn(name = "serieNumeracion")
    private Cuota cuota;
}
