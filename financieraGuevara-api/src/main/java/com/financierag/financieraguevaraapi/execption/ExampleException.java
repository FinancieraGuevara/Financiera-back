package com.financierag.financieraguevaraapi.execption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExampleException extends RuntimeException {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        // Retorna solo el mensaje de error con el status adecuado
        return new ResponseEntity<>(ex.getReason(), ex.getStatusCode());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        // Construir el mensaje personalizado con el nombre de la excepción y su mensaje
        String errorMessage = ex.getMessage();

        // Retornar solo el mensaje como JSON
        Map<String, String> response = new HashMap<>();
        response.put("error", errorMessage);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
