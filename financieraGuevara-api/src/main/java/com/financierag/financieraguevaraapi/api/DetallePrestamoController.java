package com.financierag.financieraguevaraapi.api;

import com.financierag.financieraguevaraapi.model.dto.DetallePrestamoResponseDTO;
import com.financierag.financieraguevaraapi.service.impl.DetallePrestamoServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detalleprestamos")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class DetallePrestamoController {

    private final DetallePrestamoServiceImpl detallePrestamoServiceImpl;
    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    @GetMapping
    public ResponseEntity<List<DetallePrestamoResponseDTO>> getAllDetallePrestamos(){
        List<DetallePrestamoResponseDTO> detallePrestamos = detallePrestamoServiceImpl.findAllDetallesPrestamo();
        return new ResponseEntity<>(detallePrestamos, HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    @GetMapping("/{solicitanteId}")
    public ResponseEntity<List<DetallePrestamoResponseDTO>> getDetallePrestamoById(@PathVariable int solicitanteId){
        List<DetallePrestamoResponseDTO> detallePrestamo = detallePrestamoServiceImpl.detallePrestamoSolicitante(solicitanteId);
        return new ResponseEntity<>(detallePrestamo, HttpStatus.OK);
    }
}
