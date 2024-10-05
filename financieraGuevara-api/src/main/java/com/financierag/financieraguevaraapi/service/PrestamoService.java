package com.financierag.financieraguevaraapi.service;

import com.financierag.financieraguevaraapi.model.dto.PrestamoRequestDTO;
import com.financierag.financieraguevaraapi.model.dto.PrestamoResponseDTO;
import com.financierag.financieraguevaraapi.model.entity.Prestamo;

import java.util.List;

public interface PrestamoService {
    public List<PrestamoResponseDTO> findAllPrestamos();
    public PrestamoResponseDTO findPrestamoById(int id);
    public PrestamoResponseDTO createPrestamo(int solicitanteId, PrestamoRequestDTO prestamoRequestDTO);
    public PrestamoResponseDTO updatePrestamo(int id, PrestamoRequestDTO prestamoRequestDTO);
    public void deletePrestamo(int id);

}
