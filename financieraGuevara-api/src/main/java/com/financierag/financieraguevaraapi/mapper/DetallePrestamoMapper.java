package com.financierag.financieraguevaraapi.mapper;

import com.financierag.financieraguevaraapi.model.dto.DetallePrestamoResponseDTO;
import com.financierag.financieraguevaraapi.model.entity.DetallePrestamo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DetallePrestamoMapper {

    private ModelMapper modelMapper;

    public DetallePrestamoResponseDTO convertToDTO(DetallePrestamo detallePrestamo){
        return modelMapper.map(detallePrestamo, DetallePrestamoResponseDTO.class);
    }

    public List<DetallePrestamoResponseDTO> convertToListDTO(List<DetallePrestamo> detallePrestamoList){
        return detallePrestamoList.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
