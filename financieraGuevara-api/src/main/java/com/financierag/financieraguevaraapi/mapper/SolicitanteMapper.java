package com.financierag.financieraguevaraapi.mapper;

import com.financierag.financieraguevaraapi.model.dto.SolicitanteRequestDTO;
import com.financierag.financieraguevaraapi.model.dto.SolicitanteResponseDTO;
import com.financierag.financieraguevaraapi.model.entity.Solicitante;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class SolicitanteMapper {

    private ModelMapper modelMapper;

    public Solicitante convertToEntity(SolicitanteRequestDTO solicitanteRequestDTO) {
        return modelMapper.map(solicitanteRequestDTO, Solicitante.class);
    }

    public SolicitanteResponseDTO convertToDTO(Solicitante solicitante) {
        return modelMapper.map(solicitante, SolicitanteResponseDTO.class);
    }

    public List<SolicitanteResponseDTO> convertToListDTO(List<Solicitante> solicitantes) {
        return solicitantes.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
