package com.financierag.financieraguevaraapi.mapper;

import com.financierag.financieraguevaraapi.model.dto.SolicitanteRequestDTO;
import com.financierag.financieraguevaraapi.model.entity.Solicitante;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.mapstruct.Mapping;

import java.util.Map;

@Component
@AllArgsConstructor
public class ApiMapper {
    private ModelMapper modelMapper;

    public <T> T mapToDTO(Map<String, Object> data, Class<T> dtoClass) {
        return modelMapper.map(data, dtoClass);
    }

    @Mapping(target = "fechaNacimiento", ignore = true)
    public SolicitanteRequestDTO mapToSolicitanteDTO(Map<String, Object> data) {
        return modelMapper.map(data, SolicitanteRequestDTO.class);
    }

    // Metodo para mapear SolicitanteRequestDTO a Solicitante
    public Solicitante mapToSolicitanteEntity(SolicitanteRequestDTO solicitanteRequestDTO) {
        return modelMapper.map(solicitanteRequestDTO, Solicitante.class);
    }

}
