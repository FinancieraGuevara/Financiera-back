package com.financierag.financieraguevaraapi.service.impl;

import com.financierag.financieraguevaraapi.mapper.DetallePrestamoMapper;
import com.financierag.financieraguevaraapi.mapper.ReportMapper;
import com.financierag.financieraguevaraapi.model.dto.*;
import com.financierag.financieraguevaraapi.model.entity.DetallePrestamo;
import com.financierag.financieraguevaraapi.repository.DetallePrestamoRespository;
import com.financierag.financieraguevaraapi.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {
    @Autowired
    private DetallePrestamoRespository detallePrestamoRepository;
    @Autowired
    private DetallePrestamoMapper detallePrestamoMapper;

    ReportMapper reportMapper;

    @Override
    public ReportResponseDTO generateReport(Integer userId) {

        List<DetallePrestamo> ultimoDetalle = detallePrestamoRepository.findLatestBySolicitanteId(userId);
        DetallePrestamo detallePrestamo = ultimoDetalle.get(0);
        DetallePrestamoResponseDTO detallePrestamoResponseDTO = detallePrestamoMapper.convertToDTO(detallePrestamo);

        return reportMapper.toReportResponseDTO(detallePrestamoResponseDTO);
    }

}
