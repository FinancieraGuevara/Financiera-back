package com.financierag.financieraguevaraapi.service;

import com.financierag.financieraguevaraapi.model.dto.DetallePrestamoResponseDTO;
import com.financierag.financieraguevaraapi.model.entity.DetallePrestamo;

import java.util.List;

public interface DetallePrestamoService {
    List<DetallePrestamoResponseDTO> findAllDetallesPrestamo();
    DetallePrestamoResponseDTO detallePrestamoSolicitante(int solicitanteId);
}
