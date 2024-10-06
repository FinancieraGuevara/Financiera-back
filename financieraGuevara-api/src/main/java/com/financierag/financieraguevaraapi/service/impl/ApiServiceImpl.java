package com.financierag.financieraguevaraapi.service.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.financierag.financieraguevaraapi.mapper.ApiMapper;
import com.financierag.financieraguevaraapi.model.dto.ApiResponseDTO;
import com.financierag.financieraguevaraapi.model.dto.SolicitanteRequestDTO;
import com.financierag.financieraguevaraapi.model.entity.Solicitante;
import com.financierag.financieraguevaraapi.repository.SolicitanteRepository;
import com.financierag.financieraguevaraapi.service.ApiService;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ApiServiceImpl implements ApiService {

    private RestTemplate restTemplate;
    private ApiMapper apiMapper;
    private SolicitanteRepository solicitanteRepository;
    private final String apiBaseUrl = "https://apiperu.dev/api/";
    private final String apiToken = "5fac3e28ef229c04c83ad82acced5980894b0949c0efff59d57d871aa09d6cfb";

    @Override
    public <T> ApiResponseDTO<T> getDataByType(String identifier, String type, Class<T> dtoClass) {
        ApiResponseDTO<T> apiResponse = new ApiResponseDTO<>();
        String apiUrl = apiBaseUrl + type + "/" + identifier;

        switch (type) {
            case "dni":
                Optional<Solicitante> solicitanteOpt = solicitanteRepository.findByNumero(identifier);
                if (solicitanteOpt.isPresent()) {
                    apiResponse.setSuccess(true);
                    apiResponse.setData((T) solicitanteOpt.get());
                    return apiResponse;
                }

                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + apiToken); // Agregar el token de autorización
                headers.set("Accept", "application/json"); // Aceptar respuesta en formato JSON
                headers.set("Content-Type", "application/json"); // Especificar el tipo de contenido

                // Crear una entidad HTTP con los encabezados
                HttpEntity<String> entity = new HttpEntity<>(headers);

                // Realizar la llamada al API
                ResponseEntity<Map> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, Map.class);
                Map<String, Object> responseDni = responseEntity.getBody();

                apiResponse.setSuccess((Boolean) responseDni.get("success"));

                // Mapeo de la respuesta del API a un DTO
                SolicitanteRequestDTO dniDataResponse = apiMapper.mapToDTO((Map<String, Object>) responseDni.get("data"), SolicitanteRequestDTO.class);

                Solicitante nuevoSolicitante = apiMapper.mapToSolicitanteEntity(dniDataResponse);

                solicitanteRepository.save(nuevoSolicitante);

                // Asignar el DTO a la respuesta
                apiResponse.setData((T) dniDataResponse);
                break;

            default:
                throw new IllegalArgumentException("Tipo de consulta no soportado: " + type);
        }

        return apiResponse;
    }
}
