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

    @Column(name = "ser_tip_doc_var", nullable = false)
    private String tipoDocumento;

    @Column(name = "ser_ser_var", nullable = false)
    private String serie;

    @Column(name = "ser_num_int", nullable = false)
    private int numeroCorrelativo;
}
