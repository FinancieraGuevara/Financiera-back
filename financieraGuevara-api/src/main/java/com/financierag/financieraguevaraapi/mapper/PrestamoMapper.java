package com.financierag.financieraguevaraapi.mapper;

import com.financierag.financieraguevaraapi.model.dto.CronogramaResponseDTO;
import com.financierag.financieraguevaraapi.model.dto.PrestamoRequestDTO;
import com.financierag.financieraguevaraapi.model.dto.PrestamoResponseDTO;
import com.financierag.financieraguevaraapi.model.entity.Prestamo;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PrestamoMapper {

    private ModelMapper modelMapper;
    private DetallePrestamoMapper detallePrestamoMapper;
    public Prestamo convertToEntity (PrestamoRequestDTO prestamoRequestDTO) {
        return modelMapper.map(prestamoRequestDTO, Prestamo.class);
    }

    public PrestamoResponseDTO convertToDTO (Prestamo prestamo) {
        PrestamoResponseDTO dto = modelMapper.map(prestamo, PrestamoResponseDTO.class);


        dto.setDetallecuotas(detallePrestamoMapper.convertCuotasList(prestamo.getDetallePrestamo().getCronograma()));
        dto.setSolicitanteId(prestamo.getDetallePrestamo().getSolicitante().getId());
        return dto;
    }

    public List<PrestamoResponseDTO> convertToListDTO(List<Prestamo> prestamos) {
        return prestamos.stream()
                .map(this::convertToDTO)
                .toList();
    }

}
