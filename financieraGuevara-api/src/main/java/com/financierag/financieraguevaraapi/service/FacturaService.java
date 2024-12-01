package com.financierag.financieraguevaraapi.service;

import com.financierag.financieraguevaraapi.model.dto.CronogramaResponseDTO;
import com.financierag.financieraguevaraapi.model.dto.DetallePrestamoResponseDTO;
import com.financierag.financieraguevaraapi.model.dto.ReportResponseDTO;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class FacturaService {
    public ByteArrayInputStream generateUserReportPdf(ReportResponseDTO reportResponseDTO) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DetallePrestamoResponseDTO detallePrestamoResponseDTO = reportResponseDTO.getDetallePrestamo();
        try {

            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            // Estilos para encabezados
            DeviceRgb headerColor = new DeviceRgb(0, 86, 163);
            DeviceRgb titleColor = new DeviceRgb(238, 134, 0);

            // Título principal
            document.add(new Paragraph("Factura Electrónica").setFontSize(20).setBold().setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("F001-123456").setFontSize(16).setBold().setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Fecha de Emisión: 2024-11-30").setTextAlignment(TextAlignment.LEFT));
            document.add(new Paragraph("Moneda: PEN").setTextAlignment(TextAlignment.LEFT));

            // Información del Emisor
            document.add(new Paragraph("\nInformación del Emisor").setBold());
            Table emisorTable = new Table(new float[]{1, 2});
            emisorTable.addCell(new Cell().add(new Paragraph("RUC")).setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setBold());
            emisorTable.addCell(new Cell().add(new Paragraph("20480880740")));
            emisorTable.addCell(new Cell().add(new Paragraph("Nombre")).setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setBold());
            emisorTable.addCell(new Cell().add(new Paragraph("Easy Fast Solution")));
            emisorTable.addCell(new Cell().add(new Paragraph("Razón Social")).setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setBold());
            emisorTable.addCell(new Cell().add(new Paragraph("Easy Fast Solution S.A.")));
            document.add(emisorTable);

            // Información del Cliente
            document.add(new Paragraph("\nInformación del Cliente").setBold());
            Table clienteTable = new Table(new float[]{1, 2});
            clienteTable.addCell(new Cell().add(new Paragraph("RUC")).setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setBold());
            clienteTable.addCell(new Cell().add(new Paragraph(detallePrestamoResponseDTO.getSolicitante().getNumero())));
            clienteTable.addCell(new Cell().add(new Paragraph("Razón Social")).setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setBold());
            clienteTable.addCell(new Cell().add(new Paragraph(detallePrestamoResponseDTO.getSolicitante().getNombre_completo())));
            document.add(clienteTable);

            // Detalles del Pago
            document.add(new Paragraph("\nDetalles del Pago").setBold());
            Table pagoTable = new Table(new float[]{1, 2});
            pagoTable.addCell(new Cell().add(new Paragraph("Interés")).setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setBold());
            pagoTable.addCell(new Cell().add(new Paragraph("Mora")).setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setBold());
            pagoTable.addCell(new Cell().add(new Paragraph("S/ 50.00")));
            pagoTable.addCell(new Cell().add(new Paragraph("Total a Pagar")).setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setBold());
            pagoTable.addCell(new Cell().add(new Paragraph("S/ 1,000.00")));
            document.add(pagoTable);

            // Detalles de Ítems
            document.add(new Paragraph("\nDetalles de Ítems").setBold());
            Table itemsTable = new Table(new float[]{1, 1, 2, 2, 1, 2});
            itemsTable.addCell(new Cell().add(new Paragraph("#")).setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setBold());
            itemsTable.addCell(new Cell().add(new Paragraph("Cantidad")).setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setBold());
            itemsTable.addCell(new Cell().add(new Paragraph("Descripción")).setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setBold());
            itemsTable.addCell(new Cell().add(new Paragraph("Precio")).setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setBold());
            itemsTable.addCell(new Cell().add(new Paragraph("Impuesto")).setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setBold());
            itemsTable.addCell(new Cell().add(new Paragraph("Total")).setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setBold());

            // Ejemplo de un ítem
            itemsTable.addCell(new Cell().add(new Paragraph("1")).setTextAlignment(TextAlignment.CENTER));
            itemsTable.addCell(new Cell().add(new Paragraph("1")).setTextAlignment(TextAlignment.CENTER));
            itemsTable.addCell(new Cell().add(new Paragraph("Servicio Financiero")));
            itemsTable.addCell(new Cell().add(new Paragraph("S/ 1,000.00")).setTextAlignment(TextAlignment.RIGHT));
            itemsTable.addCell(new Cell().add(new Paragraph("S/ 180.00")).setTextAlignment(TextAlignment.RIGHT));
            itemsTable.addCell(new Cell().add(new Paragraph("S/ 1,000.00")).setTextAlignment(TextAlignment.RIGHT));
            document.add(itemsTable);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
