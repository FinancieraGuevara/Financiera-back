package com.financierag.financieraguevaraapi.service;

import com.financierag.financieraguevaraapi.model.dto.PrestamoRequestDTO;
import com.financierag.financieraguevaraapi.model.dto.PrestamoResponseDTO;
import com.financierag.financieraguevaraapi.model.entity.Prestamo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PrestamoService {
    public List<PrestamoResponseDTO> findAllPrestamos();
    public PrestamoResponseDTO findPrestamoById(int id);
    public PrestamoResponseDTO createPrestamo(int solicitanteId, PrestamoRequestDTO prestamoRequestDTO);
    public PrestamoResponseDTO updatePrestamo(int id, PrestamoRequestDTO prestamoRequestDTO);
    public List<PrestamoResponseDTO> getPrestamosPendientes();
    public List<PrestamoResponseDTO> getPrestamosPagados();
    public List<PrestamoResponseDTO> getPrestamosJudiciales();
    void deletePrestamo(int prestamoId);
}
