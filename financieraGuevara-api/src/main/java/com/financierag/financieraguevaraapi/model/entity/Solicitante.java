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
@Table(name = "solicitante")
public class Solicitante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sol_id_in")
    private int id;

    @Column(name = "sol_dni_vc")
    private String numero;

    @Column(name = "sol_nom_vc")
    private String nombres;

    @Column(name = "sol_pat_vc")
    private String apellido_paterno;

    @Column(name = "sol_mat_vc")
    private String apellido_materno;

    @Column(name = "sol_fec_nac_da")
    private LocalDate fechaNacimiento;

    @Column(name = "sol_cod_vec_in")
    private String codigo_verificacion;

    @OneToMany(mappedBy = "solicitante")
    private List<DetallePrestamo> detallePrestamos;

}
