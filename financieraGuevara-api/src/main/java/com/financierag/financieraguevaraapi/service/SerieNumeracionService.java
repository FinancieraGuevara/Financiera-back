package com.financierag.financieraguevaraapi.service;

import com.financierag.financieraguevaraapi.model.entity.Cuota;
import com.financierag.financieraguevaraapi.model.entity.SerieNumeracion;
import org.springframework.stereotype.Service;

@Service
public interface SerieNumeracionService {
    SerieNumeracion generarCorrelativo(String tipoDocumento, Cuota cuota);
}
