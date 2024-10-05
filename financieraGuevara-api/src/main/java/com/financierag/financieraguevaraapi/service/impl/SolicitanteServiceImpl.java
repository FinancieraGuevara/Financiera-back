package com.financierag.financieraguevaraapi.service.impl;

import com.financierag.financieraguevaraapi.execption.ResourceNotFoundException;
import com.financierag.financieraguevaraapi.mapper.SolicitanteMapper;
import com.financierag.financieraguevaraapi.model.dto.SolicitanteRequestDTO;
import com.financierag.financieraguevaraapi.model.dto.SolicitanteResponseDTO;
import com.financierag.financieraguevaraapi.model.entity.Solicitante;
import com.financierag.financieraguevaraapi.repository.SolicitanteRepository;
import com.financierag.financieraguevaraapi.service.SolicitanteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SolicitanteServiceImpl implements SolicitanteService {

    private final SolicitanteRepository solicitanteRepository;
    private final SolicitanteMapper solicitanteMapper;

    @Override
    public List<SolicitanteResponseDTO> getAllSolicitantes() {
        List<Solicitante> solicitantes = solicitanteRepository.findAll();
        return solicitanteMapper.convertToListDTO(solicitantes);
    }

    @Override
    public SolicitanteResponseDTO getSolicitanteById(int id) {
        Solicitante solicitante = solicitanteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Localizacion no encontrada con el numero de ID"+id));
        return solicitanteMapper.convertToDTO(solicitante);
    }

    @Override
    public void deleteSolicitante(int id) {
        solicitanteRepository.deleteById(id);
    }
}
