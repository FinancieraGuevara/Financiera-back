package com.financierag.financieraguevaraapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cronograma")
public class Cuota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    int nmrcuota;
    double cuota;
    double interes;
    double capitalamortizado;
    double saldofinal;
    LocalDate fechaPago;

    @ManyToOne
    @JoinColumn(name = "det_prestamo_id")
    private DetallePrestamo detallePrestamo;

    @ManyToOne
    @JoinColumn(name = "serie_numeracion_id")
    private SerieNumeracion serieNumeracion;

    LocalDate fechadeCancelamiento;
    boolean ispayed = false;

    boolean isdeuda= false;

    @Column(nullable = true)
    boolean isJudicial= false;

    double mora=0;

    double totalmora=0;

    public boolean getIspayed()
    { return this.ispayed;}

    public boolean getIsJudicial()
    { return this.isJudicial;}
}
