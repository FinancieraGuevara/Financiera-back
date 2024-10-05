package com.financierag.financieraguevaraapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cronograma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    int nmrcuota;

    double cuota;

    LocalDate fechaPago;

    @ManyToOne
    @JoinColumn(name = "det_prestamo_id")
    private DetallePrestamo detallePrestamo;
}
