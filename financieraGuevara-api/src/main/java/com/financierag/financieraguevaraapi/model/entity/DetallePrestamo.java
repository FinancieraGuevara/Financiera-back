package com.financierag.financieraguevaraapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import  java.lang.Math;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
        double interesmensual = prestamo.getInteres() / 12;
        double cuota = prestamo.getMonto() * (interesmensual * Math.pow(1 + interesmensual, prestamo.getCuotas())) / (Math.pow(1 + interesmensual, prestamo.getCuotas()) - 1);
        double intereses = (cuota * prestamo.getCuotas()) - prestamo.getMonto();
        BigDecimal interesredondeado = new BigDecimal(intereses).setScale(2, RoundingMode.HALF_UP);
        intereses = interesredondeado.doubleValue();
        return prestamo.getMonto() + intereses;
    }

    public double calcularInteresTotal() {
        double interesmensual = prestamo.getInteres() / 12;
        double cuota = prestamo.getMonto() * (interesmensual * Math.pow(1 + interesmensual, prestamo.getCuotas())) / (Math.pow(1 + interesmensual, prestamo.getCuotas()) - 1);
        double totalintereses=(cuota * prestamo.getCuotas()) - prestamo.getMonto();
        BigDecimal totalinteresesredondeado = new BigDecimal(totalintereses).setScale(2, RoundingMode.HALF_UP);
        totalintereses = totalinteresesredondeado.doubleValue();
        return totalintereses;
    }
}
