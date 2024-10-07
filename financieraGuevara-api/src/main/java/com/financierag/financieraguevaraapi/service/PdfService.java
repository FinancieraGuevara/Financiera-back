package com.financierag.financieraguevaraapi.service;

import com.financierag.financieraguevaraapi.model.dto.CronogramaResponseDTO;
import com.financierag.financieraguevaraapi.model.dto.DetallePrestamoResponseDTO;
import com.financierag.financieraguevaraapi.model.dto.ReportResponseDTO;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
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

            /*Para el header*/
            float headerHeight = 60;
            float pageWidth = pdfDocument.getDefaultPageSize().getWidth();
            float pageHeight = pdfDocument.getDefaultPageSize().getHeight();

            PdfCanvas canvas = new PdfCanvas(pdfDocument.addNewPage());
            canvas.saveState();
            canvas.setFillColor(new DeviceRgb(17, 18, 23));
            canvas.rectangle(0, pageHeight - headerHeight, pageWidth, headerHeight);
            canvas.fill();

            /*Para el logo*/
            String logoPath = "financieraGuevara-api/src/main/resources/static/logo-FG.jpg";
            Image logo = new Image(ImageDataFactory.create(logoPath));
            logo.scaleToFit(150, 50);

            float logoX = ((pageWidth - logo.getImageWidth()) / 2)+220; // Centrar horizontalmente
            float logoY = (pageHeight - headerHeight + (headerHeight - logo.getImageHeight()) / 2)+155; // Centrar verticalmente
            logo.setFixedPosition(logoX, logoY);
            document.add(logo);

            document.add(new Paragraph("\n\nReporte de Préstamo").setFontSize(20).setBold().setTextAlignment(TextAlignment.CENTER));

            Table infoTable = new Table(new float[]{1, 1}); // Dos columnas

            infoTable.addCell(new Cell().add(new Paragraph("Solicitante")).setBold().setBackgroundColor(ColorConstants.LIGHT_GRAY).setTextAlignment(TextAlignment.CENTER));
            infoTable.addCell(new Cell().add(new Paragraph("Préstamo")).setBold().setBackgroundColor(ColorConstants.LIGHT_GRAY).setTextAlignment(TextAlignment.CENTER));

            String solicitanteInfo = "Número: " + detallePrestamoResponseDTO.getSolicitante().getNumero() +
                    "\nNombres: " + detallePrestamoResponseDTO.getSolicitante().getNombres() +
                    "\nApellido Paterno: " + detallePrestamoResponseDTO.getSolicitante().getApellido_paterno() +
                    "\nApellido Materno: " + detallePrestamoResponseDTO.getSolicitante().getApellido_materno();

            infoTable.addCell(new Cell().add(new Paragraph(solicitanteInfo)));

            String prestamoInfo = "Monto: " + detallePrestamoResponseDTO.getPrestamo().getMonto() +
                    "\nCuotas: " + detallePrestamoResponseDTO.getPrestamo().getCuotas() +
                    "\nInterés: " + detallePrestamoResponseDTO.getPrestamo().getInteres() +
                    "\nFecha de Inicio: " + detallePrestamoResponseDTO.getFechaInicio() +
                    "\nTotal a Pagar: " + detallePrestamoResponseDTO.getPagarTotal() +
                    "\nInterés Total: " + detallePrestamoResponseDTO.getInteresTotal();

            infoTable.addCell(new Cell().add(new Paragraph(prestamoInfo)));

            infoTable.setWidth(UnitValue.createPercentValue(100));

            document.add(infoTable);

            Table table = new Table(new float[]{1, 2, 2}); // Tres columnas

            Cell headerCell1 = new Cell().add(new Paragraph("Número de Cuota")).setBold().setBackgroundColor(ColorConstants.LIGHT_GRAY).setTextAlignment(TextAlignment.CENTER);
            Cell headerCell2 = new Cell().add(new Paragraph("Monto")).setBold().setBackgroundColor(ColorConstants.LIGHT_GRAY).setTextAlignment(TextAlignment.CENTER);
            Cell headerCell3 = new Cell().add(new Paragraph("Fecha de Pago")).setBold().setBackgroundColor(ColorConstants.LIGHT_GRAY).setTextAlignment(TextAlignment.CENTER);
            table.addHeaderCell(headerCell1);
            table.addHeaderCell(headerCell2);
            table.addHeaderCell(headerCell3);

            for (CronogramaResponseDTO cronograma : detallePrestamoResponseDTO.getCronograma()) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(cronograma.getNmrcuota()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(cronograma.getCuota()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(cronograma.getFechaPago()))));
            }

            table.setWidth(UnitValue.createPercentValue(100));

            document.add(new Paragraph("\nCronograma:").setBold());
            document.add(table);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
