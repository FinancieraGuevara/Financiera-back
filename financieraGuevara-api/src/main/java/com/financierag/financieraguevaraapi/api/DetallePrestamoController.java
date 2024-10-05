package com.financierag.financieraguevaraapi.api;

import com.financierag.financieraguevaraapi.model.dto.DetallePrestamoResponseDTO;
import com.financierag.financieraguevaraapi.service.impl.DetallePrestamoServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/detalleprestamos")
@AllArgsConstructor
public class DetallePrestamoController {

    private final DetallePrestamoServiceImpl detallePrestamoServiceImpl;

    @GetMapping
    public ResponseEntity<List<DetallePrestamoResponseDTO>> getAllDetallePrestamos(){
        List<DetallePrestamoResponseDTO> detallePrestamos = detallePrestamoServiceImpl.findAllDetallesPrestamo();
        return new ResponseEntity<>(detallePrestamos, HttpStatus.OK);
    }

    @GetMapping("/{solicitanteId}")
    public ResponseEntity<DetallePrestamoResponseDTO> getDetallePrestamoById(@PathVariable int solicitanteId){
        DetallePrestamoResponseDTO detallePrestamo = detallePrestamoServiceImpl.detallePrestamoSolicitante(solicitanteId);
        return new ResponseEntity<>(detallePrestamo, HttpStatus.OK);
    }
}
