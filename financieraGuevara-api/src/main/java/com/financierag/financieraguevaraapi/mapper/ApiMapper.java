package com.financierag.financieraguevaraapi.mapper;

import com.financierag.financieraguevaraapi.model.dto.SolicitanteRequestDTO;
import com.financierag.financieraguevaraapi.model.entity.Solicitante;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
public class ApiMapper {

    private ModelMapper modelMapper;

    public <T> T mapToDTO(Map<String, Object> data, Class<T> dtoClass) {
        return modelMapper.map(data, dtoClass);
    }

}
