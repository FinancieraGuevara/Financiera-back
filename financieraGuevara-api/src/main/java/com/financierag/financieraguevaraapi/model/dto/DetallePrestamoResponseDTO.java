package com.financierag.financieraguevaraapi.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.financierag.financieraguevaraapi.model.entity.Cronograma;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetallePrestamoResponseDTO {

    private int detailId;

    //@JsonProperty("solicitante")
    private SolicitanteResponseDTO solicitante;

    //@JsonProperty("prestamo")
    private PrestamoResponseDTO prestamo;

    private LocalDate fechaInicio;

    private double pagarTotal;

    private double interesTotal;

    private List<CronogramaResponseDTO> cronograma;
}
