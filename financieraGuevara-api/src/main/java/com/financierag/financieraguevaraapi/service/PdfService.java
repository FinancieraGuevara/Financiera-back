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
            canvas.setFillColor(new DeviceRgb(0, 86, 163));
            canvas.rectangle(0, pageHeight - headerHeight, pageWidth, headerHeight);
            canvas.fill();
            canvas.restoreState();

                /*Para el logo*/
                //String logoPath = "financieraGuevara-api/src/main/resources/static/logo-FG.jpg";
                //Image logo = new Image(ImageDataFactory.create(logoPath));
                //logo.scaleToFit(150, 50);
                //float logoX = ((pageWidth - logo.getImageWidth()) / 2)+220; // Centrar horizontalmente
                //float logoY = (pageHeight - headerHeight + (headerHeight - logo.getImageHeight()) / 2)+155; // Centrar verticalmente
                //logo.setFixedPosition(logoX, logoY);
                //document.add(logo);

            //Colores de las cabeceras y títulos
            DeviceRgb headerColor = new DeviceRgb(0, 86, 163);
            DeviceRgb titleColor = new DeviceRgb(238, 134, 0);

            String titleInso = "INSO";
            Paragraph titleParagraph = new Paragraph(titleInso).setFontColor(titleColor).setFontSize(20);
            float setOnX = 270;
            float setOnY = 795;
            titleParagraph.setFixedPosition(setOnX, setOnY, 400);
            document.add(titleParagraph);

            document.add(new Paragraph("\n\nReporte de Préstamo\n\n").setFontSize(20).setBold().setTextAlignment(TextAlignment.CENTER));

            Table infoTable = new Table(new float[]{1, 1}); // Dos columnas

            infoTable.addCell(new Cell().add(new Paragraph("Solicitante")).setBold().setFontColor(ColorConstants.WHITE).setBackgroundColor(headerColor).setTextAlignment(TextAlignment.CENTER));
            infoTable.addCell(new Cell().add(new Paragraph("Préstamo")).setBold().setFontColor(ColorConstants.WHITE).setBackgroundColor(headerColor).setTextAlignment(TextAlignment.CENTER));

            String solicitanteInfo = "Número: \n" + detallePrestamoResponseDTO.getSolicitante().getNumero()+"\nNombre o Razon social: \n" + detallePrestamoResponseDTO.getSolicitante().getNombre_completo();

            infoTable.addCell(new Cell().add(new Paragraph(solicitanteInfo)));

            String prestamoInfo = "Moneda: " + "Sol peruano" +
                    "\nMonto de préstamo: " + detallePrestamoResponseDTO.getPrestamo().getMonto() +
                    "\nPlazo (N° de cuotas): " + detallePrestamoResponseDTO.getPrestamo().getCuotas() +
                    "\nInterés: " + detallePrestamoResponseDTO.getPrestamo().getInteres() +
                    "\nFecha de Inicio: " + detallePrestamoResponseDTO.getFechaInicio() +
                    "\nMonto a pagar: " + detallePrestamoResponseDTO.getPagarTotal() +
                    "\nTotal intereses: " + detallePrestamoResponseDTO.getInteresTotal();

            infoTable.addCell(new Cell().add(new Paragraph(prestamoInfo)));

            infoTable.setWidth(UnitValue.createPercentValue(100));

            document.add(infoTable);

            Table table = new Table(new float[]{1, 2, 2, 1, 2, 2}); // 6 columnas

            Cell headerCell1 = new Cell().add(new Paragraph("N° de cuota")).setBold().setFontColor(ColorConstants.WHITE).setBackgroundColor(headerColor).setTextAlignment(TextAlignment.CENTER);
            Cell headerCell2 = new Cell().add(new Paragraph("Fecha de pago")).setBold().setFontColor(ColorConstants.WHITE).setBackgroundColor(headerColor).setTextAlignment(TextAlignment.CENTER);
            Cell headerCell3 = new Cell().add(new Paragraph("Cuota")).setBold().setFontColor(ColorConstants.WHITE).setBackgroundColor(headerColor).setTextAlignment(TextAlignment.CENTER);
            Cell headerCell4 = new Cell().add(new Paragraph("Interes")).setBold().setFontColor(ColorConstants.WHITE).setBackgroundColor(headerColor).setTextAlignment(TextAlignment.CENTER);
            Cell headerCell5 = new Cell().add(new Paragraph("Capital amortizado")).setBold().setFontColor(ColorConstants.WHITE).setBackgroundColor(headerColor).setTextAlignment(TextAlignment.CENTER);
            Cell headerCell6 = new Cell().add(new Paragraph("Saldo final")).setBold().setFontColor(ColorConstants.WHITE).setBackgroundColor(headerColor).setTextAlignment(TextAlignment.CENTER);

            table.addHeaderCell(headerCell1);
            table.addHeaderCell(headerCell2);
            table.addHeaderCell(headerCell3);
            table.addHeaderCell(headerCell4);
            table.addHeaderCell(headerCell5);
            table.addHeaderCell(headerCell6);

            for (CronogramaResponseDTO cronograma : detallePrestamoResponseDTO.getCronograma()) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(cronograma.getNmrcuota())).setTextAlignment(TextAlignment.CENTER)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(cronograma.getFechaPago())).setTextAlignment(TextAlignment.CENTER)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(cronograma.getCuota())).setTextAlignment(TextAlignment.CENTER)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(cronograma.getInteres())).setTextAlignment(TextAlignment.CENTER)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(cronograma.getCapitalamortizado())).setTextAlignment(TextAlignment.CENTER)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(cronograma.getSaldofinal())).setTextAlignment(TextAlignment.CENTER)));
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
