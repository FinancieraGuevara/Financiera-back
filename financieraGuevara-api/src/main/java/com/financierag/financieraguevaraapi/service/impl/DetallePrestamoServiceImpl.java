package com.financierag.financieraguevaraapi.service.impl;

import com.financierag.financieraguevaraapi.execption.ResourceNotFoundException;
import com.financierag.financieraguevaraapi.mapper.DetallePrestamoMapper;
import com.financierag.financieraguevaraapi.model.dto.DetallePrestamoResponseDTO;
import com.financierag.financieraguevaraapi.model.entity.DetallePrestamo;
import com.financierag.financieraguevaraapi.model.entity.Solicitante;
import com.financierag.financieraguevaraapi.repository.DetallePrestamoRespository;
import com.financierag.financieraguevaraapi.repository.SolicitanteRepository;
import com.financierag.financieraguevaraapi.service.DetallePrestamoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DetallePrestamoServiceImpl implements DetallePrestamoService {

    private final DetallePrestamoRespository detallePrestamoRespository;
    private final DetallePrestamoMapper detallePrestamoMapper;

    private final SolicitanteRepository solicitanteRepository;

    @Override
    public DetallePrestamoResponseDTO detallePrestamoSolicitante(int solicitanteId) {
        DetallePrestamo detallePrestamo = detallePrestamoRespository.findBySolicitanteId(solicitanteId);
        return detallePrestamoMapper.convertToDTO(detallePrestamo);
    }
}
