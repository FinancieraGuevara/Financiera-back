package com.financierag.financieraguevaraapi.service;

import com.financierag.financieraguevaraapi.model.dto.CronogramaResponseDTO;
import com.financierag.financieraguevaraapi.model.dto.DetallePrestamoResponseDTO;
import com.financierag.financieraguevaraapi.model.dto.ReportResponseDTO;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.Document;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class PdfService {
    public ByteArrayInputStream generateUserReportPdf(ReportResponseDTO reportResponseDTO) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DetallePrestamoResponseDTO detallePrestamoResponseDTO = reportResponseDTO.getDetallePrestamo();
        try {

            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            document.add(new Paragraph("Reporte de Préstamo"));

            document.add(new Paragraph("\nSolicitante:"));
            document.add(new Paragraph("\t\tNúmero: " + detallePrestamoResponseDTO.getSolicitante().getDni()));
            document.add(new Paragraph("\t\tNombres: " + detallePrestamoResponseDTO.getSolicitante().getNombre()));
            document.add(new Paragraph("\t\tApellido Paterno: " + detallePrestamoResponseDTO.getSolicitante().getApellidoPaterno()));
            document.add(new Paragraph("\t\tApellido Materno: " + detallePrestamoResponseDTO.getSolicitante().getApellidoMaterno()));

            document.add(new Paragraph("\nPréstamo:"));
            document.add(new Paragraph("\t\tMonto: " + detallePrestamoResponseDTO.getPrestamo().getMonto()));
            document.add(new Paragraph("\t\tCuotas: " + detallePrestamoResponseDTO.getPrestamo().getCuotas()));
            document.add(new Paragraph("\t\tInterés: " + detallePrestamoResponseDTO.getPrestamo().getInteres()));

            document.add(new Paragraph("\t\tFecha de Inicio: " + detallePrestamoResponseDTO.getFechaInicio()));
            document.add(new Paragraph("\t\tTotal a Pagar: " + detallePrestamoResponseDTO.getPagarTotal()));
            document.add(new Paragraph("\t\tInterés Total: " + detallePrestamoResponseDTO.getInteresTotal()));

            document.add(new Paragraph("\nCronograma:"));
            for (CronogramaResponseDTO cronograma : detallePrestamoResponseDTO.getCronograma()) {
                document.add(new Paragraph("Número de Cuota: " + cronograma.getNmrcuota()));
                document.add(new Paragraph("Cuota: " + cronograma.getCuota()));
                document.add(new Paragraph("Fecha de Pago: " + cronograma.getFechaPago()));
                document.add(new Paragraph("---"));
            }

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
