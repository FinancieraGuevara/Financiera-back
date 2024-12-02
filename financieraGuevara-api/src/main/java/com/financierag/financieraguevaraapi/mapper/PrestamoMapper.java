package com.financierag.financieraguevaraapi.mapper;

import com.financierag.financieraguevaraapi.model.dto.CronogramaResponseDTO;
import com.financierag.financieraguevaraapi.model.dto.PrestamoRequestDTO;
import com.financierag.financieraguevaraapi.model.dto.PrestamoResponseDTO;
import com.financierag.financieraguevaraapi.model.entity.Prestamo;
import com.financierag.financieraguevaraapi.repository.DetallePrestamoRespository;
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
    private DetallePrestamoRespository detallePrestamoRepository;
    public Prestamo convertToEntity (PrestamoRequestDTO prestamoRequestDTO) {
        return modelMapper.map(prestamoRequestDTO, Prestamo.class);
    }
    public Prestamo convToEntity(PrestamoResponseDTO prestamoResponseDTO)
    {  Prestamo entity =modelMapper.map(prestamoResponseDTO, Prestamo.class);
        entity.setDetallePrestamo(detallePrestamoRepository.findByPrestamo_Id(prestamoResponseDTO.getId()));
        return entity;
    }
    public PrestamoResponseDTO convertToDTO (Prestamo prestamo) {
        PrestamoResponseDTO dto = modelMapper.map(prestamo, PrestamoResponseDTO.class);


        dto.setDetallecuotas(detallePrestamoMapper.convertCuotasList(prestamo.getDetallePrestamo().getCronograma()));
        dto.setSolicitanteId(prestamo.getDetallePrestamo().getSolicitante().getId());
        dto.setUsername(prestamo.getDetallePrestamo().getSolicitante().getNombre_completo());
        return dto;
    }

    public List<PrestamoResponseDTO> convertToListDTO(List<Prestamo> prestamos) {
        return prestamos.stream()
                .map(this::convertToDTO)
                .toList();
    }
    public List<Prestamo> convertToListEntity(List<PrestamoResponseDTO> prestamos) {
        return prestamos.stream().map(this::convToEntity).toList();
    }

}
