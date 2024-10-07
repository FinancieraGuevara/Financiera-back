package com.financierag.financieraguevaraapi.service.impl;

import com.financierag.financieraguevaraapi.execption.ResourceNotFoundException;
import com.financierag.financieraguevaraapi.mapper.PrestamoMapper;
import com.financierag.financieraguevaraapi.model.dto.PrestamoRequestDTO;
import com.financierag.financieraguevaraapi.model.dto.PrestamoResponseDTO;
import com.financierag.financieraguevaraapi.model.entity.Cronograma;
import com.financierag.financieraguevaraapi.model.entity.DetallePrestamo;
import com.financierag.financieraguevaraapi.model.entity.Prestamo;
import com.financierag.financieraguevaraapi.model.entity.Solicitante;
import com.financierag.financieraguevaraapi.repository.DetallePrestamoRespository;
import com.financierag.financieraguevaraapi.repository.PrestamoRepository;
import com.financierag.financieraguevaraapi.repository.SolicitanteRepository;
import com.financierag.financieraguevaraapi.service.PrestamoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PrestamoServiceImpl implements PrestamoService {
    @Autowired
    private final PrestamoRepository prestamoRepository;
    @Autowired
    private final PrestamoMapper prestamoMapper;
    @Autowired
    private final DetallePrestamoRespository detallePrestamoRespository;
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
        prestamo.setInteres(colocarinteres(prestamo.getCuotas()));
        if (prestamo.getInteres()==0)
        {
            throw new ResourceNotFoundException("Interes no encontrado");
        }
        if(prestamo.getMonto()<0)
        {
            throw new NullPointerException("El monto no puede ser menor a 0");
        }
        Solicitante solicitante = solicitanteRepository.findById(solicitanteId)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitante no encontrado con el número de ID " + solicitanteId));


        prestamo = prestamoRepository.save(prestamo);


        DetallePrestamo detallePrestamo = new DetallePrestamo();
        detallePrestamo.setSolicitante(solicitante);
        detallePrestamo.setPrestamo(prestamo);
        detallePrestamo.setFechaInicio(LocalDate.now());


        generarCronograma(prestamo.getCuotas(), LocalDate.now(), prestamo.getMonto(), prestamo.getInteres(), detallePrestamo);


        detallePrestamoRespository.save(detallePrestamo);

        return prestamoMapper.convertToDTO(prestamo);
    }
    public double colocarinteres(int cuotas)
    {
        if(cuotas == 1)
        {
            return 0.1;
        }
        if(cuotas == 6) {
            return 0.2;
        }
        return 0;
    }
    public void generarCronograma(int cuotas, LocalDate fechaInicio, double monto, double intereses, DetallePrestamo detallePrestamo) {
        List<Cronograma> cronogramas = new ArrayList<>();
        double totalPagar = monto + (monto * intereses);

        if (cuotas == 1) {
            Cronograma cronograma = new Cronograma();
            cronograma.setCuota(totalPagar);
            cronograma.setNmrcuota(1);
            cronograma.setFechaPago(fechaInicio.plusDays(30));
            cronograma.setDetallePrestamo(detallePrestamo);
            cronogramas.add(cronograma);

        } else if (cuotas == 6) {
            double cuotaMensual = totalPagar / cuotas;
            for (int i = 1; i <= cuotas; i++) {
                Cronograma cronograma = new Cronograma();
                cronograma.setCuota(cuotaMensual);
                cronograma.setNmrcuota(i);
                cronograma.setFechaPago(fechaInicio.plusDays(30));
                fechaInicio=fechaInicio.plusDays(30);
                cronograma.setDetallePrestamo(detallePrestamo);
                cronogramas.add(cronograma);
            }
        }


        detallePrestamo.setCronograma(cronogramas);
        detallePrestamo.setPagarTotal(totalPagar);
        detallePrestamo.setInteresTotal(monto * intereses);
        // Guardar el detalle del préstamo junto con el cronograma
        detallePrestamoRespository.save(detallePrestamo);
    }
    @Override
    public PrestamoResponseDTO updatePrestamo(int id, PrestamoRequestDTO prestamoRequestDTO) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prestamo no encontrado con el numero de ID"+id));
        if(prestamoRequestDTO.getCuotas() != 0) { prestamo.setCuotas(prestamoRequestDTO.getCuotas());}
        if(prestamoRequestDTO.getMonto() != 0) { prestamo.setMonto(prestamoRequestDTO.getMonto());}
        prestamo = prestamoRepository.save(prestamo);
        return prestamoMapper.convertToDTO(prestamo);
    }

    @Override
    public void deletePrestamo(Integer prestamoId, Integer solicitanteId) {
        Optional<Prestamo> prestamoOpt = prestamoRepository.findByIdAndDetallePrestamoSolicitanteId(prestamoId, solicitanteId);
        if (prestamoOpt.isPresent()) {
            prestamoRepository.delete(prestamoOpt.get());
        } else {
            throw new EntityNotFoundException("Préstamo no encontrado para el solicitante con ID: " + solicitanteId);
        }
    }
}
