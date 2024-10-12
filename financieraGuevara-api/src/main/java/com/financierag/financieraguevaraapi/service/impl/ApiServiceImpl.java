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
                if (solicitanteOpt.isPresent() && solicitanteOpt.get().getNumero().length()==8) {
                    apiResponse.setSuccess(true);
                    apiResponse.setData((T) solicitanteOpt.get());
                    return apiResponse;
                }

                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + apiToken);
                headers.set("Accept", "application/json");
                headers.set("Content-Type", "application/json");

                HttpEntity<String> entity = new HttpEntity<>(headers);

                // Realizar la llamada al API
                ResponseEntity<Map> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, Map.class);
                Map<String, Object> responseDni = responseEntity.getBody();

                apiResponse.setSuccess((Boolean) responseDni.get("success"));

                // Mapeo de la respuesta del API a un DTO
                Map<String, Object> data = (Map<String, Object>) responseDni.get("data");
                SolicitanteRequestDTO dniDataResponse = apiMapper.mapToSolicitanteDTO(data);

                // Guardar el nuevo solicitante
                Solicitante nuevoSolicitante = apiMapper.mapToSolicitanteEntity(dniDataResponse);
                solicitanteRepository.save(nuevoSolicitante);

                // Asignar el DTO a la respuesta
                apiResponse.setData((T) dniDataResponse);
                break;

            case "ruc":
                Optional<Solicitante> solicitanteRuc = solicitanteRepository.findByNumero(identifier);
                if (solicitanteRuc.isPresent()&& solicitanteRuc.get().getNumero().length()==11) {
                    apiResponse.setSuccess(true);
                    apiResponse.setData((T) solicitanteRuc.get());
                    return apiResponse;
                }
                // Logic for RUC
                HttpHeaders rucHeaders = new HttpHeaders();
                rucHeaders.set("Authorization", "Bearer " + apiToken);
                rucHeaders.set("Accept", "application/json");
                rucHeaders.set("Content-Type", "application/json");

                HttpEntity<String> rucEntity = new HttpEntity<>(rucHeaders);

                // Realizar la llamada al API para RUC
                ResponseEntity<Map> rucResponseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, rucEntity, Map.class);
                Map<String, Object> responseRuc = rucResponseEntity.getBody();

                apiResponse.setSuccess((Boolean) responseRuc.get("success"));

                // Mapeo de la respuesta del API a un DTO para RUC
                Map<String, Object> rucData = (Map<String, Object>) responseRuc.get("data");
                SolicitanteRequestDTO rucDataResponse = new SolicitanteRequestDTO();
                rucDataResponse.setNumero((String) rucData.get("ruc")); // Set the RUC
                rucDataResponse.setNombre_completo((String) rucData.get("nombre_o_razon_social")); // Set the business name

                // Guardar el nuevo solicitante
                Solicitante nuevoSolicitanteRuc = apiMapper.mapToSolicitanteEntity(rucDataResponse);
                solicitanteRepository.save(nuevoSolicitanteRuc);

                // Asignar el DTO a la respuesta
                apiResponse.setData((T) rucDataResponse);
                break;

            default:
                throw new IllegalArgumentException("Tipo de consulta no soportado: " + type);
        }

        return apiResponse;
    }
}
