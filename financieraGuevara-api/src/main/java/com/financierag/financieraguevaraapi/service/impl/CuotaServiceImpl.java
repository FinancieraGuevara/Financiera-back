package com.financierag.financieraguevaraapi.service.impl;

import com.financierag.financieraguevaraapi.mapper.DetallePrestamoMapper;
import com.financierag.financieraguevaraapi.mapper.PrestamoMapper;
import com.financierag.financieraguevaraapi.model.dto.PrestamoResponseDTO;
import com.financierag.financieraguevaraapi.model.entity.Cuota;
import com.financierag.financieraguevaraapi.model.entity.Prestamo;
import com.financierag.financieraguevaraapi.repository.PrestamoRepository;
import com.financierag.financieraguevaraapi.service.CuotaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CuotaServiceImpl implements CuotaService {
    private final PrestamoRepository prestamoRepository;
    private final PrestamoMapper prestamoMapper;
    private final DetallePrestamoMapper detallePrestamoMapper;

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
            prestamo.setCuotasPagadas(prestamo.getCuotasPagadas()+1);
            cuota.setIspayed(true);

            if(prestamo.getCuotas()==1)
            {
                prestamo.setPayed(true);
                prestamo.setDeuda(false);
                prestamo.setJudicialDeuda(false);
                     /*Agregar la logica para generar el comprobante
                             if(prestamo.getDetallePrestamo().getSolicitante().getTipo()=="boleta")
                             {

                             }
                             if (prestamo.getDetallePrestamo().getSolicitante().getTipo()=="factura")
                      */
            }
            else
            {

                if(prestamo.getCuotasPagadas()==5)
                {
                    prestamo.setPayed(true);
                    prestamo.setDeuda(false);
                    prestamo.setJudicialDeuda(false);
                        /*Agregar la logica para generar el comprobante
                         if(prestamo.getDetallePrestamo().getSolicitante().getTipo()=="boleta")
                        {

                         }
                        if (prestamo.getDetallePrestamo().getSolicitante().getTipo()=="factura")
                        */
                }
            }

            prestamoRepository.save(prestamo);
            return prestamoMapper.convertToDTO(prestamo);
        }
        else {
            throw new NoSuchElementException("No se encontró la cuota con número " + cuotanumber);
        }
    }

}
