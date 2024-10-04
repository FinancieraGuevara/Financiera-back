package com.financierag.financieraguevaraapi.mapper;

import com.financierag.financieraguevaraapi.model.dto.PrestamoRequestDTO;
import com.financierag.financieraguevaraapi.model.dto.PrestamoResponseDTO;
import com.financierag.financieraguevaraapi.model.entity.Prestamo;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PrestamoMapper {

    private ModelMapper modelMapper;

    public Prestamo convertToEntity (PrestamoRequestDTO prestamoRequestDTO) {
        return modelMapper.map(prestamoRequestDTO, Prestamo.class);
    }

    public PrestamoResponseDTO convertToDTO (Prestamo prestamo) {
        return modelMapper.map(prestamo, PrestamoResponseDTO.class);
    }

    public List<PrestamoResponseDTO> convertToListDTO(List<Prestamo> prestamos) {
        return prestamos.stream()
                .map(this::convertToDTO)
                .toList();
    }

}
