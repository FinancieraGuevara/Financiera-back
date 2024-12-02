package com.financierag.financieraguevaraapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "serieNumeracion")
public class SerieNumeracion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Integer boleta;


    private Integer factura;

    @ManyToOne
    @JoinColumn(name = "serieNumeracion")
    private Cuota cuota;
}
