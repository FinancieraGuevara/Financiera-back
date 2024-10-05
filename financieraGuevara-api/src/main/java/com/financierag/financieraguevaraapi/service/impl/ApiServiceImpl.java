package com.financierag.financieraguevaraapi.service.impl;

import com.financierag.financieraguevaraapi.mapper.SolicitanteMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
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

    private final SolicitanteMapper solicitanteMapper;
    private RestTemplate restTemplate;
    private ApiMapper apiMapper;
    private SolicitanteRepository solicitanteRepository;
    private final String apiBaseUrl = "https://apiperu.dev/api/";
    private final String apiToken = "31304751b641467ac93462682254c1fbde971071d4923e08225fa41d67a1ad7d";

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
                try{
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
                    if (!apiResponse.isSuccess()) {
                        String message = (String) responseDni.get("message");
                        apiResponse.setMessage(message);
                    } else {
                        // Mapeo de la respuesta del API a un DTO
                        SolicitanteRequestDTO dniDataResponse = apiMapper.mapToDTO((Map<String, Object>) responseDni.get("data"), SolicitanteRequestDTO.class);

                        Solicitante nuevoSolicitante = solicitanteMapper.convertToEntity(dniDataResponse);

                        solicitanteRepository.save(nuevoSolicitante);
                        apiResponse.setMessage("DNI encontrado.");
                        // Asignar el DTO a la respuesta
                        apiResponse.setData((T) dniDataResponse);
                    }

                } catch (HttpClientErrorException e) {
                    apiResponse.setSuccess(false);
                    apiResponse.setMessage("Error de cliente: " + e.getResponseBodyAsString());
                    System.out.println("Error: " + e.getStatusCode());
                    System.out.println("Cuerpo de la respuesta: " + e.getResponseBodyAsString());
                } catch (HttpServerErrorException e) {
                    apiResponse.setSuccess(false);
                    apiResponse.setMessage("Error del servidor: " + e.getResponseBodyAsString());
                    System.out.println("Error: " + e.getStatusCode());
                    System.out.println("Cuerpo de la respuesta: " + e.getResponseBodyAsString());
                } catch (RestClientException e) {
                    apiResponse.setSuccess(false);
                    apiResponse.setMessage("Ocurrió un error inesperado: " + e.getMessage());
                    e.printStackTrace();
                }
                break;
        }

        return apiResponse;
    }
}
