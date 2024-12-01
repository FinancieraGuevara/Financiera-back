package com.financierag.financieraguevaraapi.api;

import com.financierag.financieraguevaraapi.mapper.DetallePrestamoMapper;
import com.financierag.financieraguevaraapi.mapper.PrestamoMapper;
import com.financierag.financieraguevaraapi.model.dto.ReportResponseDTO;
import com.financierag.financieraguevaraapi.model.entity.Prestamo;
import com.financierag.financieraguevaraapi.repository.PrestamoRepository;
import com.financierag.financieraguevaraapi.service.BoletaService;
import com.financierag.financieraguevaraapi.service.FacturaService;
import com.financierag.financieraguevaraapi.service.PdfService;
import com.financierag.financieraguevaraapi.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = {"https://fguevara-guevara.web.app","http://localhost:4200"}, allowCredentials = "true")
@RequestMapping("/reports")
public class PdfController {
    private final BoletaService boletaService;
    private final FacturaService facturaService;
    private final ReportService reportService;
    private final PrestamoRepository prestamoRepository;
    private final PdfService pdfService;

    @CrossOrigin(origins = {"https://fguevara-guevara.web.app","http://localhost:4200"}, allowCredentials = "true")
    @GetMapping("/pdf/{userId}")
    public ResponseEntity<InputStreamResource> downloadUserReportPdf(@PathVariable Integer userId) {
        ReportResponseDTO reportResponseDTO = reportService.generateReport(userId);
        ByteArrayInputStream pdfStream = pdfService.generateUserReportPdf(reportResponseDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=Financiera_Guevara_" + userId + "_cronograma.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }

    @GetMapping("/cuota/{prestamoId}/{nroCuota}")
    public ResponseEntity<InputStreamResource> downloadPagoCuotaPdf(@PathVariable Integer prestamoId, @PathVariable Integer nroCuota) {
        ReportResponseDTO reportResponseDTO = reportService.generateComprobante(prestamoId, nroCuota);
        String userName = reportResponseDTO.getDetallePrestamo().getSolicitante().getNombre_completo();

        Prestamo prestamo = prestamoRepository.findById(prestamoId).orElse(null);
        if (prestamo == null) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayInputStream pdfStream;

        if ("boleta".equals(prestamo.getDetallePrestamo().getSolicitante().getTipo())) {
            pdfStream = boletaService.generateUserReportPdf(reportResponseDTO);
        } else if ("factura".equals(prestamo.getDetallePrestamo().getSolicitante().getTipo())) {
            pdfStream = facturaService.generateUserReportPdf(reportResponseDTO);
        } else {
            return ResponseEntity.badRequest().build(); // Manejo para tipos no reconocidos
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=FG_cronograma.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }
}
