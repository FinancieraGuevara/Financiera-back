package com.financierag.financieraguevaraapi.service;

import com.financierag.financieraguevaraapi.model.dto.DetallePrestamoResponseDTO;
import com.financierag.financieraguevaraapi.model.entity.DetallePrestamo;

public interface DetallePrestamoService {
    DetallePrestamoResponseDTO detallePrestamoSolicitante(int solicitanteId);
}
