package com.financierag.financieraguevaraapi.api;

import com.financierag.financieraguevaraapi.mapper.DetallePrestamoMapper;
import com.financierag.financieraguevaraapi.mapper.PrestamoMapper;
import com.financierag.financieraguevaraapi.model.dto.PrestamoResponseDTO;
import com.financierag.financieraguevaraapi.model.dto.ReportResponseDTO;
import com.financierag.financieraguevaraapi.model.entity.Cuota;
import com.financierag.financieraguevaraapi.model.entity.Prestamo;
import com.financierag.financieraguevaraapi.repository.PrestamoRepository;
import com.financierag.financieraguevaraapi.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor

@RequestMapping("/reports")
public class PdfController {
    private final BoletaService boletaService;
    private final FacturaService facturaService;
    private final ReportService reportService;
    private final PrestamoRepository prestamoRepository;
    private final PdfService pdfService;
    private final PrestamoService prestamoService;


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

    @GetMapping("/prestamo/{prestamoId}/cuota/{nroCuota}")
    public ResponseEntity<InputStreamResource> downloadPagoCuotaPdf(@PathVariable Integer prestamoId, @PathVariable Integer nroCuota) {
        ReportResponseDTO reportResponseDTO = reportService.generateComprobante(prestamoId, nroCuota);
        String userName = reportResponseDTO.getDetallePrestamo().getSolicitante().getNombre_completo();

        Prestamo prestamo = prestamoRepository.findById(prestamoId).orElse(null);
        if (prestamo == null) {
            return ResponseEntity.notFound().build();
        }

        List<Cuota> cuotas = prestamo.getDetallePrestamo().getCronograma();

        Optional<Cuota> cuotaOptional = cuotas.stream()
                .filter(c -> c.getNmrcuota() == nroCuota)
                .findFirst();
        if(cuotaOptional.isPresent())
        {
            Cuota cuota = cuotaOptional.get();
            if(!cuota.getIspayed())
            {
                throw new IllegalArgumentException("No puedes generar comprobante de una cuota que no esta pagada");
            }
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
