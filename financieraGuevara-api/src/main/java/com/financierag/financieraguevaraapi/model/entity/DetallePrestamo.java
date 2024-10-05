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
@Table(name = "detalle_prestamo")
public class DetallePrestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "det_id_in")
    private int detailId;

    @ManyToOne
    @JoinColumn(name = "det_sol_id_in")
    private Solicitante solicitante;

    @OneToOne
    @JoinColumn(name = "det_pre_id_in")
    private Prestamo prestamo;

    @Column(name = "det_fech_pag_dt", updatable = false)
    private LocalDate fechaInicio;
}
