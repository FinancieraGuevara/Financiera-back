package com.financierag.financieraguevaraapi.service;

import com.financierag.financieraguevaraapi.model.dto.ReportResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface ReportService {
    ReportResponseDTO generateReport(Integer userId);
}
