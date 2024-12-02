package com.financierag.financieraguevaraapi.api;

import com.financierag.financieraguevaraapi.model.dto.ApiResponseDTO;
import com.financierag.financieraguevaraapi.model.dto.SolicitanteRequestDTO;
import com.financierag.financieraguevaraapi.service.ApiService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/private")

public class ApiController {
    private final ApiService apiService;


    @GetMapping("/consulta/{identifier}")
    public ResponseEntity<?> getDataById(@PathVariable String identifier, @RequestParam String type) {
        try {
            ApiResponseDTO<SolicitanteRequestDTO> response = apiService.getDataByType(identifier, type, SolicitanteRequestDTO.class);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Documento no encontrado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la solicitud: " + e.getMessage());
        }
    }
}