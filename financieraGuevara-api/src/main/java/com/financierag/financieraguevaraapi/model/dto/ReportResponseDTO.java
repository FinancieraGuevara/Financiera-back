package com.financierag.financieraguevaraapi.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReportResponseDTO {
    private String message;
    private String reportUrl;
    private DetallePrestamoResponseDTO detallePrestamo;
    private CronogramaResponseDTO cronogramaResponseDTO;
}
