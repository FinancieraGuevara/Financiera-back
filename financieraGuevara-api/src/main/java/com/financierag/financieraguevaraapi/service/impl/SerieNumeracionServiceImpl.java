package com.financierag.financieraguevaraapi.service.impl;

import com.financierag.financieraguevaraapi.model.entity.SerieNumeracion;
import com.financierag.financieraguevaraapi.repository.SerieNumeracionRepository;
import com.financierag.financieraguevaraapi.service.SerieNumeracionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SerieNumeracionServiceImpl implements SerieNumeracionService {
    @Autowired
    private SerieNumeracionRepository serieNumeracionRepository;

    public String generarNumeroDocumento(String tipoDocumento) {
        // Obtener la numeración existente para el tipo de documento
        SerieNumeracion serieNumeracion = serieNumeracionRepository.findByTipoDocumento(tipoDocumento)
                .orElseGet(() -> {
                    // Crear una nueva entrada si no existe
                    SerieNumeracion nuevaSerieNumeracion = new SerieNumeracion();
                    nuevaSerieNumeracion.setSerie(tipoDocumento.equals("FACTURA") ? "F001" : "B001");
                    nuevaSerieNumeracion.setNumeroCorrelativo(0);
                    nuevaSerieNumeracion.setTipoDocumento(tipoDocumento);
                    return serieNumeracionRepository.save(nuevaSerieNumeracion);
                });

        // Incrementar el número correlativo
        int nuevoNumero = serieNumeracion.getNumeroCorrelativo() + 1;
        serieNumeracion.setNumeroCorrelativo(nuevoNumero);
        serieNumeracionRepository.save(serieNumeracion);

        // Retornar la numeración completa
        return String.format("%s-%09d", serieNumeracion.getSerie(), nuevoNumero);
    }
}
