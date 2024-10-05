package com.financierag.financieraguevaraapi.api;

import com.financierag.financieraguevaraapi.model.dto.PrestamoRequestDTO;
import com.financierag.financieraguevaraapi.model.dto.PrestamoResponseDTO;
import com.financierag.financieraguevaraapi.service.impl.PrestamoServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestamos")
@AllArgsConstructor
public class PrestamoController {

    private final PrestamoServiceImpl prestamoServiceImpl;

    @GetMapping
    public ResponseEntity<List<PrestamoResponseDTO>> getAllPrestamos() {
        List<PrestamoResponseDTO> prestamos = prestamoServiceImpl.findAllPrestamos();
        return new ResponseEntity<>(prestamos, HttpStatus.OK);
    }

   @GetMapping("/{id}")
   public ResponseEntity<PrestamoResponseDTO> getPrestamoById(@PathVariable int id) {
        PrestamoResponseDTO prestamo = prestamoServiceImpl.findPrestamoById(id);
        return new ResponseEntity<>(prestamo, HttpStatus.OK);
   }

   @PostMapping("/crear/{solicitanteId}")
   public ResponseEntity<PrestamoResponseDTO> createPrestamo(@PathVariable int solicitanteId, @RequestBody PrestamoRequestDTO prestamoRequestDTO) {
        PrestamoResponseDTO prestamo = prestamoServiceImpl.createPrestamo(solicitanteId,prestamoRequestDTO);
        return new ResponseEntity<>(prestamo, HttpStatus.CREATED);
   }

   @PutMapping("/{id}")
   public ResponseEntity<PrestamoResponseDTO> updatePrestamo(@PathVariable int id,
                                                             @RequestBody PrestamoRequestDTO prestamoRequestDTO) {
        PrestamoResponseDTO prestamo = prestamoServiceImpl.updatePrestamo(id,prestamoRequestDTO);
        return new ResponseEntity<>(prestamo, HttpStatus.OK);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<PrestamoResponseDTO> deletePrestamo(@PathVariable int id) {
        prestamoServiceImpl.deletePrestamo(id);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);

   }
}
