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
    private String nombre_completo;


}
