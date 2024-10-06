package com.financierag.financieraguevaraapi.api;

import com.financierag.financieraguevaraapi.model.dto.ApiResponseDTO;
import com.financierag.financieraguevaraapi.model.dto.SolicitanteRequestDTO;
import com.financierag.financieraguevaraapi.service.ApiService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/private")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ApiController {

    private ApiService apiService;
    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    @GetMapping("/consulta/{identifier}")
    public ResponseEntity<?> getDataById(@PathVariable String identifier, @RequestParam String type) {
        try {
            if ("dni".equalsIgnoreCase(type)) {
                ApiResponseDTO<SolicitanteRequestDTO> response = apiService.getDataByType(identifier, type, SolicitanteRequestDTO.class);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body("Tipo de consulta no soportado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la solicitud: " + e.getMessage());
        }
    }
}
