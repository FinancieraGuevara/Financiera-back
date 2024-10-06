package com.financierag.financieraguevaraapi.api;

import com.financierag.financieraguevaraapi.model.dto.ReportResponseDTO;
import com.financierag.financieraguevaraapi.service.PdfService;
import com.financierag.financieraguevaraapi.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@AllArgsConstructor
@RequestMapping("/reports")
public class PdfController {
    private final PdfService pdfService;
    private final ReportService reportService;

    @GetMapping("/user/{userId}")
    public ReportResponseDTO generateUserReport(@PathVariable Integer userId) {
        return reportService.generateReport(userId);
    }

    @GetMapping("/pdf/{userId}")
    public ResponseEntity<InputStreamResource> downloadUserReportPdf(@PathVariable Integer userId) {
        ReportResponseDTO reportResponseDTO = reportService.generateReport(userId);
        ByteArrayInputStream pdfStream = pdfService.generateUserReportPdf(reportResponseDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=user_report_" + userId + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }
}
