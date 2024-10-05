package com.financierag.financieraguevaraapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prestamo")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pre_id_in")
    private int id;

    @Column(name = "pre_mon_mo")
    private double monto;

    @Column(name = "pre_cuo_in")
    private int cuotas;

    @Column(name = "pre_int_mo")
    private double interes;

    @OneToOne(mappedBy = "prestamo", cascade = CascadeType.ALL)
    private DetallePrestamo detallePrestamo;

}
