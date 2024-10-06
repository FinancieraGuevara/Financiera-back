package com.financierag.financieraguevaraapi.model.dto;

import lombok.Data;

@Data
public class ReportResponseDTO {
    private String message;
    private String reportUrl;
    private DetallePrestamoResponseDTO detallePrestamo;

}
