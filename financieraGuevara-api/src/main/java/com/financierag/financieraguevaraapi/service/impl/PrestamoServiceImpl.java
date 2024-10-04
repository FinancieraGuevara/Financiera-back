package com.financierag.financieraguevaraapi.service.impl;

import com.financierag.financieraguevaraapi.execption.ResourceNotFoundException;
import com.financierag.financieraguevaraapi.mapper.DetallePrestamoMapper;
import com.financierag.financieraguevaraapi.mapper.PrestamoMapper;
import com.financierag.financieraguevaraapi.model.dto.PrestamoRequestDTO;
import com.financierag.financieraguevaraapi.model.dto.PrestamoResponseDTO;
import com.financierag.financieraguevaraapi.model.entity.DetallePrestamo;
import com.financierag.financieraguevaraapi.model.entity.Prestamo;
import com.financierag.financieraguevaraapi.model.entity.Solicitante;
import com.financierag.financieraguevaraapi.repository.DetallePrestamoRespository;
import com.financierag.financieraguevaraapi.repository.PrestamoRepository;
import com.financierag.financieraguevaraapi.repository.SolicitanteRepository;
import com.financierag.financieraguevaraapi.service.PrestamoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final PrestamoMapper prestamoMapper;

    private final DetallePrestamoRespository detallePrestamoRespository;
    private final DetallePrestamoMapper detallePrestamoMapper;

    private final SolicitanteRepository solicitanteRepository;
    @Override
    public List<PrestamoResponseDTO> findAllPrestamos() {
        List<Prestamo> prestamos = prestamoRepository.findAll();
        return prestamoMapper.convertToListDTO(prestamos);
    }

    @Override
    public PrestamoResponseDTO findPrestamoById(int id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prestamo no encontrado con el numero de ID"+id));
        return prestamoMapper.convertToDTO(prestamo);
    }

    @Override
    public PrestamoResponseDTO createPrestamo(int solicitanteId, PrestamoRequestDTO prestamoRequestDTO) {
        Prestamo prestamo = prestamoMapper.convertToEntity(prestamoRequestDTO);
        Solicitante solicitante = solicitanteRepository.findById(solicitanteId)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitant no encontrado con el numero de ID"+solicitanteId));
        DetallePrestamo detallePrestamo = new DetallePrestamo();
        detallePrestamo.setSolicitante(solicitante);
        prestamo.setDetallePrestamo(detallePrestamo);

        prestamo = prestamoRepository.save(prestamo);
        return prestamoMapper.convertToDTO(prestamo);
    }

    @Override
    public PrestamoResponseDTO updatePrestamo(int id, PrestamoRequestDTO prestamoRequestDTO) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prestamo no encontrado con el numero de ID"+id));
        if(prestamoRequestDTO.getCuotas() != 0) { prestamo.setCuotas(prestamoRequestDTO.getCuotas());}
        if(prestamoRequestDTO.getMonto() != 0) { prestamo.setMonto(prestamoRequestDTO.getMonto());}
        if(prestamoRequestDTO.getInteres() != 0) { prestamo.setInteres(prestamoRequestDTO.getInteres());}
        prestamo = prestamoRepository.save(prestamo);
        return prestamoMapper.convertToDTO(prestamo);
    }

    @Override
    public void deletePrestamo(int id) {
        prestamoRepository.deleteById(id);
    }
}
