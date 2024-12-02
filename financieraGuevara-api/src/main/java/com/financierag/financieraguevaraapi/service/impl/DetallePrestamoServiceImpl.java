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
import java.util.Optional;


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
    public DetallePrestamoResponseDTO findDetallePrestamoById(int id) {
        DetallePrestamo detallePrestamo = detallePrestamoRespository.findByPrestamo_Id(id);
        return detallePrestamoMapper.convertToDTO(detallePrestamo);
    }

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
        test();
    }

    public void test()
    {
        LocalDate today = LocalDate.now();

        System.out.println(today);
        List<Prestamo> prestamos = prestamoMapper.convertToListEntity(prestamoService.findAllPrestamos());
        for (Prestamo prestamo : prestamos) {
            if(prestamo.isCompleted())
            {

            }
            else
            {
                List<Cuota> cuotas = prestamo.getDetallePrestamo().getCronograma();
                for (Cuota cuota : cuotas) {
                    if (cuota.getFechaPago().isBefore(today) && !cuota.getIspayed()&&!cuota.getIsJudicial()) // || cuota.getFechaPago().isEqual(today) "vencer hoy"
                    {
                        cuota.setIsdeuda(true);


                        cuota.setMora(0.01*cuota.getCuota());

                        long diasDeDeuda = ChronoUnit.DAYS.between(cuota.getFechaPago(), today);
                        cuota.setTotaldiasmora(diasDeDeuda);
                        if (diasDeDeuda > 0) {
                            cuota.setTotalmora(cuota.getMora() * diasDeDeuda);

                        } else {
                            cuota.setTotalmora(0);

                        }
                        prestamo.setDeuda(true);
                        prestamo.setPayed(false);
                        prestamo.setCuotasporpagar(Totalofcuotas(prestamo.getDetallePrestamo().getCronograma()));

                        if (today.isAfter(cuota.getFechaPago().plusYears(1))) {
                            prestamo.setJudicialDeuda(true);
                            cuota.setJudicial(true);
                            prestamo.setCuotasJudicialesporpagar(TotalofcuotasJudiciales(prestamo.getDetallePrestamo().getCronograma()));

                        }

                    }
                }
            }
            prestamoRepository.save(prestamo);
        }
    }

    public void sixteenmonths()
    {
        LocalDate today = LocalDate.now().plusMonths(16);
        System.out.println(today);
        List<Prestamo> prestamos = prestamoMapper.convertToListEntity(prestamoService.findAllPrestamos());
        for (Prestamo prestamo : prestamos) {
            if(prestamo.isCompleted())
            {

            }
            else
            {
                List<Cuota> cuotas = prestamo.getDetallePrestamo().getCronograma();
                for (Cuota cuota : cuotas) {
                    if (cuota.getFechaPago().isBefore(today) && !cuota.getIspayed()&&!cuota.getIsJudicial()) // || cuota.getFechaPago().isEqual(today) "vencer hoy"
                    {
                        cuota.setIsdeuda(true);

                        cuota.setMora(0.01*cuota.getCuota());

                        long diasDeDeuda = ChronoUnit.DAYS.between(cuota.getFechaPago(), today);
                        cuota.setTotaldiasmora(diasDeDeuda);
                        if (diasDeDeuda > 0) {
                            cuota.setTotalmora(cuota.getMora() * diasDeDeuda);

                        } else {
                            cuota.setTotalmora(0);

                        }
                        prestamo.setDeuda(true);
                        prestamo.setPayed(false);
                        prestamo.setCuotasporpagar(Totalofcuotas(prestamo.getDetallePrestamo().getCronograma()));

                        if (today.isAfter(cuota.getFechaPago().plusYears(1))) {
                            prestamo.setJudicialDeuda(true);
                            cuota.setJudicial(true);
                            prestamo.setCuotasJudicialesporpagar(TotalofcuotasJudiciales(prestamo.getDetallePrestamo().getCronograma()));

                        }

                    }
                }
            }
            prestamoRepository.save(prestamo);
        }
    }
    public void twoyear()
    {
        LocalDate today = LocalDate.now().plusYears(2);
        System.out.println(today);
        List<Prestamo> prestamos = prestamoMapper.convertToListEntity(prestamoService.findAllPrestamos());
        for (Prestamo prestamo : prestamos) {
            if(prestamo.isCompleted())
            {

            }
            else
            {
                List<Cuota> cuotas = prestamo.getDetallePrestamo().getCronograma();
                for (Cuota cuota : cuotas) {
                    if (cuota.getFechaPago().isBefore(today) && !cuota.getIspayed()&&!cuota.getIsJudicial()) // || cuota.getFechaPago().isEqual(today) "vencer hoy"
                    {
                        cuota.setIsdeuda(true);

                        cuota.setMora(0.01*cuota.getCuota());

                        long diasDeDeuda = ChronoUnit.DAYS.between(cuota.getFechaPago(), today);

                        cuota.setTotaldiasmora(diasDeDeuda);

                        if (diasDeDeuda > 0) {
                            cuota.setTotalmora(cuota.getMora() * diasDeDeuda);

                        } else {
                            cuota.setTotalmora(0);

                        }
                        prestamo.setDeuda(true);
                        prestamo.setPayed(false);
                        prestamo.setCuotasporpagar(Totalofcuotas(prestamo.getDetallePrestamo().getCronograma()));

                        if (today.isAfter(cuota.getFechaPago().plusYears(1))) {
                            prestamo.setJudicialDeuda(true);
                            cuota.setJudicial(true);
                            prestamo.setCuotasJudicialesporpagar(TotalofcuotasJudiciales(prestamo.getDetallePrestamo().getCronograma()));

                        }

                    }
                }
            }
            prestamoRepository.save(prestamo);
        }
    }

    public void customMonths(int i)
    {
        LocalDate today = LocalDate.now().plusMonths(i);
        System.out.println(today);
        List<Prestamo> prestamos = prestamoMapper.convertToListEntity(prestamoService.findAllPrestamos());
        for (Prestamo prestamo : prestamos) {
           if(!prestamo.isCompleted())
            {
                List<Cuota> cuotas = prestamo.getDetallePrestamo().getCronograma();
                for (Cuota cuota : cuotas) {
                    if (cuota.getFechaPago().isBefore(today) && !cuota.getIspayed()&&!cuota.getIsJudicial()) // || cuota.getFechaPago().isEqual(today) "vencer hoy"
                    {
                        cuota.setIsdeuda(true);

                        cuota.setMora(0.01*cuota.getCuota());

                        long diasDeDeuda = ChronoUnit.DAYS.between(cuota.getFechaPago(), today);

                        cuota.setTotaldiasmora(diasDeDeuda);

                        if (diasDeDeuda > 0) {
                            cuota.setTotalmora(cuota.getMora() * diasDeDeuda);

                        } else {
                            cuota.setTotalmora(0);

                        }
                        prestamo.setDeuda(true);
                        prestamo.setPayed(false);
                        prestamo.setCuotasporpagar(Totalofcuotas(prestamo.getDetallePrestamo().getCronograma()));

                        if (today.isAfter(cuota.getFechaPago().plusYears(1))) {
                            prestamo.setJudicialDeuda(true);
                            cuota.setJudicial(true);
                            prestamo.setCuotasJudicialesporpagar(TotalofcuotasJudiciales(prestamo.getDetallePrestamo().getCronograma()));

                        }

                    }
                }
            }
            prestamoRepository.save(prestamo);
        }
    }

    public int TotalofcuotasJudiciales(List<Cuota> cuotas )
    {   int total_judiciales=0;


        for(Cuota cuota : cuotas)
        {
           if(cuota.getIsJudicial())
           {
               total_judiciales++;
           }
        }
        return total_judiciales;
    }

    public int Totalofcuotas(List<Cuota> cuotas)
    {
        int total_cuotas_por_pagar=0;


        for(Cuota cuota : cuotas)
        {
            if(cuota.getIsDeuda())
            {
                total_cuotas_por_pagar++;
            }
        }
        return total_cuotas_por_pagar;
    }
}
