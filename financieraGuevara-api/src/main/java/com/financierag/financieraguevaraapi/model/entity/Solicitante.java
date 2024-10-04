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
    private String dni;

    @Column(name = "sol_nom_vc")
    private String nombre;

    @Column(name = "sol_pat_vc")
    private String apellidoPaterno;

    @Column(name = "sol_mat_vc")
    private String apellidoMaterno;

    @Column(name = "sol_fec_nac_da")
    private LocalDate fechaNacimiento;

    @OneToMany(mappedBy = "solicitante")
    private List<DetallePrestamo> detallePrestamos;

}
