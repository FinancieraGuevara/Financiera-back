package com.financierag.financieraguevaraapi.service.impl;

import com.financierag.financieraguevaraapi.mapper.DetallePrestamoMapper;
import com.financierag.financieraguevaraapi.mapper.PrestamoMapper;
import com.financierag.financieraguevaraapi.model.dto.PrestamoResponseDTO;
import com.financierag.financieraguevaraapi.model.entity.Cuota;
import com.financierag.financieraguevaraapi.model.entity.Prestamo;
import com.financierag.financieraguevaraapi.model.entity.SerieNumeracion;
import com.financierag.financieraguevaraapi.repository.CronogramaRepository;
import com.financierag.financieraguevaraapi.repository.PrestamoRepository;
import com.financierag.financieraguevaraapi.repository.SerieNumeracionRepository;
import com.financierag.financieraguevaraapi.service.CuotaService;
import com.financierag.financieraguevaraapi.service.SerieNumeracionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CuotaServiceImpl implements CuotaService {
    private final PrestamoRepository prestamoRepository;
    private final PrestamoMapper prestamoMapper;
    private SerieNumeracionService serieNumeracionService;
    private CronogramaRepository cronogramaRepository;

    @Override
    public PrestamoResponseDTO SetCoutaPayed(int prestamoId, int cuotanumber) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId).orElse(null);
        if(prestamo==null)
        {
            throw new NoSuchElementException("No se encontro el prestamo");
        }

        if(cuotanumber>prestamo.getCuotas())
        {
            throw new NoSuchElementException("No se encontro la cuota");
        }

        List<Cuota> cuotas = prestamo.getDetallePrestamo().getCronograma();

        Optional<Cuota> cuotaOptional = cuotas.stream()
                .filter(c -> c.getNmrcuota() == cuotanumber)
                .findFirst();

        if (cuotaOptional.isPresent()) {
            Cuota cuota = cuotaOptional.get();
            if(cuota.getIspayed())
            {
                throw new IllegalArgumentException("No puedes pagar una cuota que esta pagada");
            }
            if(prestamo.isDeuda()&& !cuota.getIsDeuda())
            {
                throw new IllegalArgumentException("Paga primero la(s) cuota(s) que tienen deuda");
            }
            prestamo.setCuotasPagadas(prestamo.getCuotasPagadas()+1);
            cuota.setIspayed(true);
            cuota.setIsdeuda(false);

            cuota.setFechadeCancelamiento(LocalDate.now());

            if(cuota.getIsJudicial())
            {
                prestamo.setCuotasJudicialesPagadas(prestamo.getCuotasJudicialesPagadas()+1);
            }
            cuota.setJudicial(false);
            if(prestamo.getCuotas()==1)
            {
                prestamo.setPayed(true);
                prestamo.setDeuda(false);
                prestamo.setJudicialDeuda(false);

            }
            else
            {

                if(prestamo.getCuotasPagadas()>=prestamo.getCuotasporpagar()&&prestamo.getCuotasJudicialesPagadas()>=prestamo.getCuotasJudicialesporpagar())
                {
                    prestamo.setPayed(true);
                    prestamo.setDeuda(false);

                }
                if(prestamo.getCuotasJudicialesPagadas()==prestamo.getCuotasJudicialesporpagar())
                {
                    prestamo.setJudicialDeuda(false);

                }
                if(prestamo.getCuotasPagadas()==prestamo.getCuotas())
                {
                    prestamo.setCompleted(true);
                }
            }

            if (cuota.getSerieNumeracion() == null) {
                String tipoDocumento = prestamo.getDetallePrestamo().getSolicitante().getTipo();
                SerieNumeracion serieNumeracion = serieNumeracionService.generarCorrelativo(tipoDocumento, cuota);
                cuota.setSerieNumeracion(serieNumeracion);  // Asignar la SerieNumeracion a la cuota
                cronogramaRepository.save(cuota); // Guardar la cuota con su SerieNumeracion

            }

            prestamoRepository.save(prestamo);
            return prestamoMapper.convertToDTO(prestamo);
        }
        else {
            throw new NoSuchElementException("No se encontró la cuota con número " + cuotanumber);
        }
    }

}
