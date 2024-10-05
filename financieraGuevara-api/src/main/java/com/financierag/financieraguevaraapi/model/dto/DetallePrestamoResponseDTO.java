package com.financierag.financieraguevaraapi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class DetallePrestamoResponseDTO {

    private int detailId;

    //@JsonProperty("solicitante")
    private SolicitanteResponseDTO solicitante;

    //@JsonProperty("prestamo")
    private PrestamoResponseDTO prestamo;

    private LocalDate fechaInicio;
}
