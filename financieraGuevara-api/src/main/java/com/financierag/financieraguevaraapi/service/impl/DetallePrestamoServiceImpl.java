package com.financierag.financieraguevaraapi.service.impl;

import com.financierag.financieraguevaraapi.execption.ResourceNotFoundException;
import com.financierag.financieraguevaraapi.mapper.DetallePrestamoMapper;
import com.financierag.financieraguevaraapi.mapper.PrestamoMapper;
import com.financierag.financieraguevaraapi.model.dto.CronogramaResponseDTO;
import com.financierag.financieraguevaraapi.model.dto.DetallePrestamoResponseDTO;
import com.financierag.financieraguevaraapi.model.dto.PrestamoResponseDTO;
import com.financierag.financieraguevaraapi.model.entity.Cuota;
import com.financierag.financieraguevaraapi.model.entity.DetallePrestamo;
import com.financierag.financieraguevaraapi.model.entity.Prestamo;
import com.financierag.financieraguevaraapi.model.entity.Solicitante;
import com.financierag.financieraguevaraapi.repository.CronogramaRepository;
import com.financierag.financieraguevaraapi.repository.DetallePrestamoRespository;
import com.financierag.financieraguevaraapi.repository.PrestamoRepository;
import com.financierag.financieraguevaraapi.repository.SolicitanteRepository;
import com.financierag.financieraguevaraapi.service.DetallePrestamoService;
import com.financierag.financieraguevaraapi.service.PrestamoService;
import com.financierag.financieraguevaraapi.service.SolicitanteService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;


@AllArgsConstructor
@EnableScheduling
@Service
public class DetallePrestamoServiceImpl implements DetallePrestamoService {
    @Autowired
    private final DetallePrestamoRespository detallePrestamoRespository;
    @Autowired
    private final DetallePrestamoMapper detallePrestamoMapper;
    @Autowired
    private final PrestamoService prestamoService;
    @Autowired
    private final PrestamoRepository prestamoRepository;
    @Autowired
    private final PrestamoMapper prestamoMapper;
    @Override
    public List<DetallePrestamoResponseDTO> findAllDetallesPrestamo() {
        List<DetallePrestamo> detallePrestamos = detallePrestamoRespository.findAll();
        return detallePrestamoMapper.convertToListDTO(detallePrestamos);
    }

    @Override
    public List<DetallePrestamoResponseDTO>  detallePrestamoSolicitante(int solicitanteId) {
        List<DetallePrestamo> detallePrestamo = detallePrestamoRespository.findBySolicitanteId(solicitanteId);
        return detallePrestamoMapper.convertToListDTO(detallePrestamo);
    }
    @Scheduled(fixedRate = 60000) // Por ejemplo, cada 24 horas -> 86400000 || 60000 = 60 segundos
    @PostConstruct
    public void scheduledCuotas() {
        System.out.println("Ejecutando la tarea programada para calcular deudas");
        sixmonths();
    }

    public void test()
    {
        LocalDate today = LocalDate.now();

        System.out.println(today);
        List<Prestamo> prestamos = prestamoMapper.convertToListEntity(prestamoService.findAllPrestamos());
        for (Prestamo prestamo : prestamos) {
            if(prestamo.isPayed())
            {

            }
            else
            {
                List<Cuota> cuotas = prestamo.getDetallePrestamo().getCronograma();
                for (Cuota cuota : cuotas) {
                    if (cuota.getFechaPago().isBefore(today) && !cuota.getIspayed()) // || cuota.getFechaPago().isEqual(today) "vencer hoy"
                    {
                        cuota.setIsdeuda(true);

                        cuota.setMora(0.01*cuota.getCuota());

                        long diasDeDeuda = ChronoUnit.DAYS.between(cuota.getFechaPago(), today);

                        if (diasDeDeuda > 0) {
                            cuota.setTotalmora(cuota.getMora() * diasDeDeuda);

                        } else {
                            cuota.setTotalmora(0);

                        }
                        prestamo.setDeuda(true);

                        if (today.isAfter(cuota.getFechaPago().plusDays(365))) {

                            prestamo.setJudicialDeuda(true);
                        }

                    }
                }
            }
            prestamoRepository.save(prestamo);
        }
    }

    public void sixmonths()
    {
        LocalDate today = LocalDate.now().plusMonths(6);
        System.out.println(today);
        List<Prestamo> prestamos = prestamoMapper.convertToListEntity(prestamoService.findAllPrestamos());
        for (Prestamo prestamo : prestamos) {
            if(prestamo.isPayed())
            {

            }
            else
            {
                List<Cuota> cuotas = prestamo.getDetallePrestamo().getCronograma();
                for (Cuota cuota : cuotas) {
                    if (cuota.getFechaPago().isBefore(today) && !cuota.getIspayed()) // || cuota.getFechaPago().isEqual(today) "vencer hoy"
                    {
                        cuota.setIsdeuda(true);

                        cuota.setMora(0.01*cuota.getCuota());

                        long diasDeDeuda = ChronoUnit.DAYS.between(cuota.getFechaPago(), today);

                        if (diasDeDeuda > 0) {
                            cuota.setTotalmora(cuota.getMora() * diasDeDeuda);

                        } else {
                            cuota.setTotalmora(0);

                        }
                        prestamo.setDeuda(true);

                        if(cuota.getFechaPago().isBefore(cuota.getFechaPago().plusYears(1)))
                        {
                            prestamo.setJudicialDeuda(true);
                        }

                    }
                }
            }
            prestamoRepository.save(prestamo);
        }
    }
    public void oneyear()
    {
        LocalDate today = LocalDate.now().plusYears(1);
        System.out.println(today);
        List<Prestamo> prestamos = prestamoMapper.convertToListEntity(prestamoService.findAllPrestamos());
        for (Prestamo prestamo : prestamos) {
            if(prestamo.isPayed())
            {

            }
            else
            {
                List<Cuota> cuotas = prestamo.getDetallePrestamo().getCronograma();
                for (Cuota cuota : cuotas) {
                    if (cuota.getFechaPago().isBefore(today) && !cuota.getIspayed()) // || cuota.getFechaPago().isEqual(today) "vencer hoy"
                    {
                        cuota.setIsdeuda(true);

                        cuota.setMora(0.01*cuota.getCuota());

                        long diasDeDeuda = ChronoUnit.DAYS.between(cuota.getFechaPago(), today);

                        if (diasDeDeuda > 0) {
                            cuota.setTotalmora(cuota.getMora() * diasDeDeuda);

                        } else {
                            cuota.setTotalmora(0);

                        }
                        prestamo.setDeuda(true);

                        if(cuota.getFechaPago().isBefore(cuota.getFechaPago().plusYears(1)))
                        {
                            prestamo.setJudicialDeuda(true);
                        }

                    }
                }
            }
            prestamoRepository.save(prestamo);
        }
    }
}
