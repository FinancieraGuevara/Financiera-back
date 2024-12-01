package com.financierag.financieraguevaraapi.service;

import com.financierag.financieraguevaraapi.model.dto.CronogramaResponseDTO;
import com.financierag.financieraguevaraapi.model.dto.DetallePrestamoResponseDTO;
import com.financierag.financieraguevaraapi.model.dto.ReportResponseDTO;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

@Service
public class BoletaService {
    @Autowired
    private SerieNumeracionService serieNumeracionService;

    public ByteArrayInputStream generateUserReportPdf(ReportResponseDTO reportResponseDTO) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DetallePrestamoResponseDTO detallePrestamoResponseDTO = reportResponseDTO.getDetallePrestamo();
        CronogramaResponseDTO cronogramaResponseDTO = reportResponseDTO.getCronogramaResponseDTO();
        String solicitanteNumero = detallePrestamoResponseDTO.getSolicitante().getNumero();
        String solicitanteNombre = detallePrestamoResponseDTO.getSolicitante().getNombre_completo();
        double cuota = cronogramaResponseDTO.getCuota();
        double mora = cronogramaResponseDTO.getMora();
        double totalmora=cronogramaResponseDTO.getTotalmora();
        double interes = cronogramaResponseDTO.getInteres();
        int nmrcuota = cronogramaResponseDTO.getNmrcuota();
        try {

            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            String numeroFactura = serieNumeracionService.generarNumeroDocumento("BOLETA");

            // Estilos para encabezados
            DeviceRgb headerColor = new DeviceRgb(0, 86, 163);

            Table headerTable = new Table(new float[]{2, 2});
            headerTable.setWidth(UnitValue.createPercentValue(100));

            // Columna izquierda
            headerTable.addCell(new Cell().add(new Paragraph("Easy Fast Solution").setBold().setFontSize(16)).setBorder(Border.NO_BORDER));
            headerTable.addCell(new Cell().add(new Paragraph("RUC. 20480880740").setBold()).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));

            headerTable.addCell(new Cell().add(new Paragraph("Av. America 365").setFontSize(12)).setBorder(Border.NO_BORDER));
            headerTable.addCell(new Cell().add(new Paragraph("Boleta Electrónica").setBold()).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));

            headerTable.addCell(new Cell().add(new Paragraph("Teléfono: 990009909").setFontSize(10)).setBorder(Border.NO_BORDER));
            headerTable.addCell(new Cell().add(new Paragraph(numeroFactura).setFontSize(12).setBold()).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));

            document.add(headerTable);


            document.add(new Paragraph("\nDatos del Cliente").setBold());
            Table clienteTable = new Table(new float[]{1, 2});
            clienteTable.setWidth(UnitValue.createPercentValue(100));
            clienteTable.addCell(new Cell().add(new Paragraph("Nombre: "+solicitanteNombre)).setBold().setBorder(Border.NO_BORDER));
            clienteTable.addCell(new Cell().add(new Paragraph("Fecha de Emisión: "+ LocalDate.now())).setBorder(Border.NO_BORDER));
            clienteTable.addCell(new Cell().add(new Paragraph("DNI. "+solicitanteNumero)).setBold().setBorder(Border.NO_BORDER));
            clienteTable.addCell(new Cell().add(new Paragraph("Tipo de moneda: PEN")).setBorder(Border.NO_BORDER));

            document.add(clienteTable);

            // Detalles del Pago
            document.add(new Paragraph("\nDetalles del Pago").setBold());
            Table pagoTable = new Table(new float[]{1, 2});
            pagoTable.setWidth(UnitValue.createPercentValue(40));

            pagoTable.addCell(new Cell().add(new Paragraph("Concepto"))
                    .setBackgroundColor(headerColor)
                    .setFontColor(ColorConstants.WHITE)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER));
            pagoTable.addCell(new Cell().add(new Paragraph("Monto"))
                    .setBackgroundColor(headerColor)
                    .setFontColor(ColorConstants.WHITE)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER));

            pagoTable.addCell(new Cell().add(new Paragraph("Interés"))
                    .setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
            pagoTable.addCell(new Cell().add(new Paragraph("S/ " + interes))
                    .setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));

            pagoTable.addCell(new Cell().add(new Paragraph("Mora"))
                    .setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
            pagoTable.addCell(new Cell().add(new Paragraph(String.format("S/ %.2f" , mora)))
                    .setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));

            pagoTable.addCell(new Cell().add(new Paragraph("Total Mora"))
                    .setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
            pagoTable.addCell(new Cell().add(new Paragraph(String.format("S/ %.2f" ,totalmora)))
                    .setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));

            pagoTable.addCell(new Cell().add(new Paragraph("Monto cuota"))
                    .setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
            pagoTable.addCell(new Cell().add(new Paragraph("S/ " + cuota))
                    .setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));

            document.add(pagoTable);

            document.add(new Paragraph("\n"));

            Table itemsTable = new Table(new float[]{1, 1, 2, 1, 2});
            itemsTable.setWidth(UnitValue.createPercentValue(100));
            itemsTable.addCell(new Cell().add(new Paragraph(" ")).setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setBold());
            itemsTable.addCell(new Cell().add(new Paragraph("Cantidad")).setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setBold());
            itemsTable.addCell(new Cell().add(new Paragraph("Descripción")).setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setBold());
            itemsTable.addCell(new Cell().add(new Paragraph("Subtotal")).setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setBold());
            itemsTable.addCell(new Cell().add(new Paragraph("Total")).setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setBold());

            itemsTable.addCell(new Cell().add(new Paragraph("1")).setTextAlignment(TextAlignment.CENTER));
            itemsTable.addCell(new Cell().add(new Paragraph("1")).setTextAlignment(TextAlignment.CENTER));
            itemsTable.addCell(new Cell().add(new Paragraph("Pago de cuota N°" + nmrcuota + " relacionada al préstamo N°" + detallePrestamoResponseDTO.getPrestamo().getId())));
            itemsTable.addCell(new Cell().add(new Paragraph(String.format("S/ %.2f", cuota))).setTextAlignment(TextAlignment.RIGHT));
            itemsTable.addCell(new Cell().add(new Paragraph(String.format("S/ %.2f", cuota + totalmora))).setTextAlignment(TextAlignment.RIGHT));
            document.add(itemsTable);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
