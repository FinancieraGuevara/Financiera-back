package com.financierag.financieraguevaraapi.mapper;

import com.financierag.financieraguevaraapi.model.dto.CronogramaResponseDTO;
import com.financierag.financieraguevaraapi.model.dto.DetallePrestamoResponseDTO;
import com.financierag.financieraguevaraapi.model.dto.ReportResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


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

    public ReportResponseDTO toCronogramaResponseDTO(CronogramaResponseDTO cronogramaResponseDTO) {
        ReportResponseDTO responseDTO = new ReportResponseDTO();
        // Asignamos el detalle del préstamo al DTO
        responseDTO.setCronogramaResponseDTO(cronogramaResponseDTO);

        // Establecemos el mensaje de éxito
        responseDTO.setMessage("Comprobante de préstamo generado exitosamente.");
        responseDTO.setReportUrl("/reports/prestamo_comprobante.pdf");

        return responseDTO;
    }

}
