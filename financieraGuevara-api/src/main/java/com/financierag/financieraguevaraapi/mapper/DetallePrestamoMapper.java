package com.financierag.financieraguevaraapi.mapper;

import com.financierag.financieraguevaraapi.model.dto.CronogramaResponseDTO;
import com.financierag.financieraguevaraapi.model.dto.DetallePrestamoResponseDTO;
import com.financierag.financieraguevaraapi.model.entity.Cronograma;
import com.financierag.financieraguevaraapi.model.entity.DetallePrestamo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DetallePrestamoMapper {

    private final ModelMapper modelMapper;

    public DetallePrestamoResponseDTO convertToDTO(DetallePrestamo detallePrestamo) {
        DetallePrestamoResponseDTO dto = modelMapper.map(detallePrestamo, DetallePrestamoResponseDTO.class);

        // Calcular pagarTotal e interesTotal
        dto.setPagarTotal(detallePrestamo.calcularPagarTotal());
        dto.setInteresTotal(detallePrestamo.calcularInteresTotal());

        // Convertir la lista de Cronograma a CronogramaResponseDTO
        List<CronogramaResponseDTO> cronogramasDTO = detallePrestamo.getCronograma().stream()
                .map(this::convertCronogramaToDTO)
                .collect(Collectors.toList());

        dto.setCronograma(cronogramasDTO);

        return dto;
    }

    private CronogramaResponseDTO convertCronogramaToDTO(Cronograma cronograma) {
        return modelMapper.map(cronograma, CronogramaResponseDTO.class);
    }

    public List<DetallePrestamoResponseDTO> convertToListDTO(List<DetallePrestamo> detallePrestamoList) {
        return detallePrestamoList.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
