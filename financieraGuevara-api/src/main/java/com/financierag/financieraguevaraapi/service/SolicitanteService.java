package com.financierag.financieraguevaraapi.service;

import com.financierag.financieraguevaraapi.model.dto.DeudoresDTO;
import com.financierag.financieraguevaraapi.model.dto.SolicitanteRequestDTO;
import com.financierag.financieraguevaraapi.model.dto.SolicitanteResponseDTO;
import com.financierag.financieraguevaraapi.model.entity.Solicitante;

import java.util.List;

public interface SolicitanteService {
    public List<SolicitanteResponseDTO> getAllSolicitantes();
    public SolicitanteResponseDTO getSolicitanteById(int id);
    public void deleteSolicitante(int id);
    List<SolicitanteResponseDTO> obtenerDeudores();
    List<DeudoresDTO> obtenerDeudoresYDinero();
}
