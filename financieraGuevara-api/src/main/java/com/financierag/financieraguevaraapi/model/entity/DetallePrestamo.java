package com.financierag.financieraguevaraapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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

    @OneToMany(mappedBy = "detallePrestamo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cronograma> cronograma;

    @Transient
    private double pagarTotal;
    @Transient

    private double interesTotal;
    public double calcularPagarTotal() {
        // Implementa la lógica para calcular el total a pagar
        // Por ejemplo:
        return prestamo.getMonto() + (prestamo.getMonto() * prestamo.getInteres());
    }

    public double calcularInteresTotal() {
        // Implementa la lógica para calcular el interés total
        return prestamo.getMonto() * prestamo.getInteres();
    }
}
