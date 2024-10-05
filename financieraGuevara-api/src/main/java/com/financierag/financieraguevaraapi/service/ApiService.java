package com.financierag.financieraguevaraapi.service;

import com.financierag.financieraguevaraapi.model.dto.ApiResponseDTO;

public interface ApiService {
    <T> ApiResponseDTO<T> getDataByType(String identifier, String type, Class<T> dtoClass);
}
