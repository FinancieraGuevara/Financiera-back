package com.financierag.financieraguevaraapi.mapper;

import com.financierag.financieraguevaraapi.model.dto.DetallePrestamoResponseDTO;
import com.financierag.financieraguevaraapi.model.dto.ReportResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
public class ReportMapper {

    public ReportResponseDTO toReportResponseDTO(DetallePrestamoResponseDTO detallePrestamo) {
        ReportResponseDTO responseDTO = new ReportResponseDTO();
        // Asignamos el detalle del préstamo al DTO
        responseDTO.setDetallePrestamo(detallePrestamo);

        // Establecemos el mensaje de éxito
        responseDTO.setMessage("Reporte de préstamo generado exitosamente.");

        // Construimos la URL del reporte, basándonos en el ID del detalle del préstamo (por ejemplo, el ID del solicitante o del préstamo)
        responseDTO.setReportUrl("/reports/prestamo_" + detallePrestamo.getDetailId() + "_reporte.pdf");

        return responseDTO;
    }

}
