package com.financierag.financieraguevaraapi.model.dto;

import java.time.LocalDate;

public class DetallePrestamoResponseDTO {
    private int detailId;
    private SolicitanteResponseDTO solicitante;
    private PrestamoResponseDTO prestamo;
    private LocalDate fechaInicio;
}
