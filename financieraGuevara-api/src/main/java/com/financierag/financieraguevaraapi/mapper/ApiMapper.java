package com.financierag.financieraguevaraapi.mapper;

import com.financierag.financieraguevaraapi.model.dto.SolicitanteRequestDTO;
import com.financierag.financieraguevaraapi.model.entity.Solicitante;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.mapstruct.Mapping;

import java.util.Map;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class ApiMapper {
    @Autowired
    private ModelMapper modelMapper;

    private void configureMappings() {
        modelMapper.addMappings(new PropertyMap<SolicitanteRequestDTO, Solicitante>() {
            @Override
            protected void configure() {
                map(source.getNumero(), destination.getNumero()); // Asegúrate de que estos nombres coincidan
                map(source.getNombre_completo(), destination.getNombre_completo()); // Ajusta el campo según sea necesario
            }
        });
    }

    public <T> T mapToDTO(Map<String, Object> data, Class<T> dtoClass) {
        return modelMapper.map(data, dtoClass);
    }

    public SolicitanteRequestDTO mapToSolicitanteDTO(Map<String, Object> data) {
        return modelMapper.map(data, SolicitanteRequestDTO.class);
    }

    public Solicitante mapToSolicitanteEntity(SolicitanteRequestDTO solicitanteRequestDTO) {
        Solicitante solicitante = new Solicitante();
        solicitante.setNumero(solicitanteRequestDTO.getNumero());
        solicitante.setNombre_completo(solicitanteRequestDTO.getNombre_completo());
        return solicitante;
    }
}