package com.financierag.financieraguevaraapi.api;

import com.financierag.financieraguevaraapi.model.dto.ReportResponseDTO;
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
    private final PdfService pdfService;
    private final ReportService reportService;
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
}
