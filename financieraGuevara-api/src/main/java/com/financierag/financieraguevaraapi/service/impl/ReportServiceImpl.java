package com.financierag.financieraguevaraapi.service.impl;

import com.financierag.financieraguevaraapi.mapper.DetallePrestamoMapper;
import com.financierag.financieraguevaraapi.mapper.ReportMapper;
import com.financierag.financieraguevaraapi.model.dto.*;
import com.financierag.financieraguevaraapi.model.entity.Cuota;
import com.financierag.financieraguevaraapi.model.entity.DetallePrestamo;
import com.financierag.financieraguevaraapi.model.entity.Prestamo;
import com.financierag.financieraguevaraapi.repository.DetallePrestamoRespository;
import com.financierag.financieraguevaraapi.repository.PrestamoRepository;
import com.financierag.financieraguevaraapi.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {
    @Autowired
    private DetallePrestamoRespository detallePrestamoRepository;
    @Autowired
    private DetallePrestamoMapper detallePrestamoMapper;
    private final PrestamoRepository prestamoRepository;
    ReportMapper reportMapper;

    @Override
    public ReportResponseDTO generateReport(Integer userId) {

        List<DetallePrestamo> ultimoDetalle = detallePrestamoRepository.findLatestBySolicitanteId(userId);
        DetallePrestamo detallePrestamo = ultimoDetalle.get(0);
        DetallePrestamoResponseDTO detallePrestamoResponseDTO = detallePrestamoMapper.convertToDTO(detallePrestamo);

        return reportMapper.toReportResponseDTO(detallePrestamoResponseDTO);
    }

    @Override
    public ReportResponseDTO generateComprobante(Integer prestamoId, Integer nroCuota) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId).orElse(null);
        if(prestamo==null) {
            throw new NoSuchElementException("No se encontro el prestamo");
        }

        if(nroCuota>prestamo.getCuotas())
        {
            throw new NoSuchElementException("No se encontro la cuota");
        }

        List<Cuota> cuotas = prestamo.getDetallePrestamo().getCronograma();
        Optional<Cuota> cuotaOptional = cuotas.stream()
                .filter(c -> c.getNmrcuota() == nroCuota)
                .findFirst();

        Cuota cuota = cuotaOptional.get();

        CronogramaResponseDTO cronogramaResponseDTO = detallePrestamoMapper.convertCronogramaToDTO(cuota);

        DetallePrestamoResponseDTO detallePrestamoResponseDTO = detallePrestamoMapper.convertToDTO(prestamo.getDetallePrestamo());

        ReportResponseDTO reportResponseDTO = new ReportResponseDTO();
        reportResponseDTO.setMessage("Comprobante de cuota generado exitosamente.");
        reportResponseDTO.setDetallePrestamo(detallePrestamoResponseDTO);
        reportResponseDTO.setCronogramaResponseDTO(cronogramaResponseDTO);

        return reportResponseDTO;
    }

}
