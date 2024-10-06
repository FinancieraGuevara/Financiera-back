package com.financierag.financieraguevaraapi.api;

import com.financierag.financieraguevaraapi.model.dto.ApiResponseDTO;
import com.financierag.financieraguevaraapi.model.dto.SolicitanteRequestDTO;
import com.financierag.financieraguevaraapi.service.ApiService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ApiController {

    private ApiService apiService;

    @GetMapping("/consulta/{identifier}")
    public <T> ApiResponseDTO<T> getDataById(@PathVariable String identifier, @RequestParam String type) {
        if ("dni".equalsIgnoreCase(type)) {
            return (ApiResponseDTO<T>) apiService.getDataByType(identifier, type, SolicitanteRequestDTO.class);
        } else {
            throw new IllegalArgumentException("Tipo de consulta no soportado: " + type);
        }
    }
}
