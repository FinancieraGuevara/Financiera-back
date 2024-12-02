package com.financierag.financieraguevaraapi.service.impl;

import com.financierag.financieraguevaraapi.model.entity.Cuota;
import com.financierag.financieraguevaraapi.model.entity.SerieNumeracion;
import com.financierag.financieraguevaraapi.repository.CronogramaRepository;
import com.financierag.financieraguevaraapi.repository.SerieNumeracionRepository;
import com.financierag.financieraguevaraapi.service.SerieNumeracionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SerieNumeracionServiceImpl implements SerieNumeracionService {

    @Autowired
    private SerieNumeracionRepository serieNumeracionRepository;

    @Autowired
    private CronogramaRepository cuotaRepository;  // Para guardar la cuota

    @Override
    public SerieNumeracion generarCorrelativo(String tipoDocumento, Cuota cuota) {
        SerieNumeracion nuevaSerie = new SerieNumeracion(); // Nueva instancia de SerieNumeracion

        // Inicializamos la variable para el nuevo correlativo
        Integer nuevoCorrelativo = null;

        // Dependiendo del tipo de documento (boleta o factura)
        if ("boleta".equals(tipoDocumento)) {
            // Obtener todos los registros con boleta no nulo
            List<SerieNumeracion> seriesBoletas = serieNumeracionRepository.findByBoletaIsNotNull();

            if (!seriesBoletas.isEmpty()) {
                // Obtener el valor más alto de la columna boleta
                nuevoCorrelativo = seriesBoletas.stream()
                        .mapToInt(SerieNumeracion::getBoleta)
                        .max()
                        .orElse(0);  // Si no hay ningún valor, iniciamos desde 0
            } else {
                // Si no existe ningún valor, iniciamos desde 1
                nuevoCorrelativo = 0;
            }

            // Asignar el nuevo correlativo a la boleta
            nuevaSerie.setBoleta(nuevoCorrelativo + 1);
            nuevaSerie.setFactura(null); // Dejar factura como null
        } else if ("factura".equals(tipoDocumento)) {
            // Obtener todos los registros con factura no nulo
            List<SerieNumeracion> seriesFacturas = serieNumeracionRepository.findByFacturaIsNotNull();

            if (!seriesFacturas.isEmpty()) {
                // Obtener el valor más alto de la columna factura
                nuevoCorrelativo = seriesFacturas.stream()
                        .mapToInt(SerieNumeracion::getFactura)
                        .max()
                        .orElse(0);  // Si no hay ningún valor, iniciamos desde 0
            } else {
                // Si no existe ningún valor, iniciamos desde 1
                nuevoCorrelativo = 0;
            }

            // Asignar el nuevo correlativo a la factura
            nuevaSerie.setFactura(nuevoCorrelativo + 1);
            nuevaSerie.setBoleta(null); // Dejar boleta como null
        }

        // Guardar la nueva serie de numeración
        SerieNumeracion serieGuardada = serieNumeracionRepository.save(nuevaSerie);

        // Asociar la serie a la cuota
        cuota.setSerieNumeracion(serieGuardada);

        // Guardar la cuota con la nueva serie asociada
        cuotaRepository.save(cuota);

        return serieGuardada;
    }
}