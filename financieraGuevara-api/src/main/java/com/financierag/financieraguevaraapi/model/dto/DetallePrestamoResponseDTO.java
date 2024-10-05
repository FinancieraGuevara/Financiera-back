package com.financierag.financieraguevaraapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetallePrestamoResponseDTO {
    private int detailId;
    private SolicitanteResponseDTO solicitante;
    private PrestamoResponseDTO prestamo;
    private LocalDate fechaInicio;
}
