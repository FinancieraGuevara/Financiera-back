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
import java.lang.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
        fechaInicio= fechaInicio.minusDays(1);

            double interesmensual=(intereses)/12;
        double cuota = monto * ((interesmensual * Math.pow(1 + interesmensual, 6)) / (Math.pow(1 + interesmensual, 6) - 1));
            BigDecimal cuotaredondeada= new BigDecimal(cuota).setScale(2, RoundingMode.HALF_UP);
            cuota=cuotaredondeada.doubleValue();
            double interesestotales=(cuota*cuotas)-monto;
        double totalPagar = monto + interesestotales;
        double saldoinicial= monto;

        if (cuotas == 1) {
                double interesmensualcuota = saldoinicial*interesmensual;
                double capitalamortizado=cuota-(saldoinicial*interesmensual);
                double saldofinal=saldoinicial-capitalamortizado;

            Cronograma cronograma = new Cronograma();
                BigDecimal redondearcuota = new BigDecimal(cuota).setScale(2, RoundingMode.HALF_UP);
                cuota = redondearcuota.doubleValue();
            cronograma.setCuota(cuota);
            cronograma.setNmrcuota(1);
                BigDecimal redondearinteresmensual = new BigDecimal(interesmensualcuota).setScale(2, RoundingMode.HALF_UP);
                interesmensualcuota = redondearinteresmensual.doubleValue();
            cronograma.setInteres(interesmensualcuota);
                BigDecimal redondearcapitalmortizado = new BigDecimal(capitalamortizado).setScale(2, RoundingMode.HALF_UP);
                capitalamortizado=redondearcapitalmortizado.doubleValue();
            cronograma.setCapitalamortizado(capitalamortizado);
                BigDecimal redondearsaldofinal = new BigDecimal(saldofinal).setScale(2, RoundingMode.HALF_UP);
                saldofinal=redondearsaldofinal.doubleValue();
            if (saldofinal <= 0.01) {
                saldofinal = 0.0;
            }
            cronograma.setSaldofinal(saldofinal);
            // no es necesario porque solo es 1 cuota --> saldoinicial=saldofinal;
            cronograma.setFechaPago(fechaInicio.plusMonths(1));
            cronograma.setDetallePrestamo(detallePrestamo);
            cronogramas.add(cronograma);

        } else if (cuotas == 6) {
            for (int i = 1; i <= cuotas; i++) {
                    double interesmensualcuota = saldoinicial*interesmensual;
                    double capitalamortizado=cuota-(saldoinicial*interesmensual);
                    double saldofinal=saldoinicial-capitalamortizado;
                Cronograma cronograma = new Cronograma();
                    BigDecimal redondearcuota = new BigDecimal(cuota).setScale(2, RoundingMode.HALF_UP);
                    cuota = redondearcuota.doubleValue();
                cronograma.setCuota(cuota);
                cronograma.setNmrcuota(i);
                    BigDecimal redondearinteresmensual = new BigDecimal(interesmensualcuota).setScale(2, RoundingMode.HALF_UP);
                    interesmensualcuota = redondearinteresmensual.doubleValue();
                cronograma.setInteres(interesmensualcuota);
                    BigDecimal redondearcapitalmortizado = new BigDecimal(capitalamortizado).setScale(2, RoundingMode.HALF_UP);
                capitalamortizado=redondearcapitalmortizado.doubleValue();
                cronograma.setCapitalamortizado(capitalamortizado);
                    BigDecimal redondearsaldofinal = new BigDecimal(saldofinal).setScale(2, RoundingMode.HALF_UP);
                    saldofinal=redondearsaldofinal.doubleValue();
                if (saldofinal <= 0.01) {
                    saldofinal = 0.0;
                }
                cronograma.setSaldofinal(saldofinal);

                saldoinicial=saldofinal;

                cronograma.setFechaPago(fechaInicio.plusMonths(1));
                fechaInicio=fechaInicio.plusMonths(1);
                cronograma.setDetallePrestamo(detallePrestamo);
                cronogramas.add(cronograma);
            }
        }

        detallePrestamo.setCronograma(cronogramas);
        detallePrestamo.setPagarTotal(totalPagar);
        detallePrestamo.setInteresTotal(interesestotales);
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
    public void deletePrestamo(int id) {
        prestamoRepository.deleteById(id);
    }

}
