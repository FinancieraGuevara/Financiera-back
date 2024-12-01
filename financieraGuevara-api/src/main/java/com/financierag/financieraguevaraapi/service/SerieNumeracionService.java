package com.financierag.financieraguevaraapi.service;

import org.springframework.stereotype.Service;

public interface SerieNumeracionService {
    String generarNumeroDocumento(String tipoDocumento);
}
