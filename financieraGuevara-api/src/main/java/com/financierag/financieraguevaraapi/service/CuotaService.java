package com.financierag.financieraguevaraapi.service;

import com.financierag.financieraguevaraapi.model.dto.PrestamoResponseDTO;

public interface CuotaService {
    //cuando le añadas el pdf le cambias el tipo de retorno del metodo
    public PrestamoResponseDTO SetCoutaPayed(int prestamoId, int cuotanumber);
}
