package com.financierag.financieraguevaraapi.api;

import com.financierag.financieraguevaraapi.model.dto.PrestamoRequestDTO;
import com.financierag.financieraguevaraapi.model.dto.PrestamoResponseDTO;
import com.financierag.financieraguevaraapi.service.impl.PrestamoServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestamos")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class PrestamoController {
    @Autowired
    private final PrestamoServiceImpl prestamoServiceImpl;

    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    @GetMapping
    public ResponseEntity<List<PrestamoResponseDTO>> getAllPrestamos() {
        List<PrestamoResponseDTO> prestamos = prestamoServiceImpl.findAllPrestamos();
        return new ResponseEntity<>(prestamos, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
   @GetMapping("/{id}")
   public ResponseEntity<PrestamoResponseDTO> getPrestamoById(@PathVariable int id) {
        PrestamoResponseDTO prestamo = prestamoServiceImpl.findPrestamoById(id);
        return new ResponseEntity<>(prestamo, HttpStatus.OK);
   }
   @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
   @PostMapping("/crear/{solicitanteId}")
   public ResponseEntity<PrestamoResponseDTO> createPrestamo(@PathVariable int solicitanteId, @RequestBody PrestamoRequestDTO prestamoRequestDTO) {
        PrestamoResponseDTO prestamo = prestamoServiceImpl.createPrestamo(solicitanteId,prestamoRequestDTO);
        return new ResponseEntity<>(prestamo, HttpStatus.CREATED);
   }
    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
   @PutMapping("/{id}")
   public ResponseEntity<PrestamoResponseDTO> updatePrestamo(@PathVariable int id,
                                                             @RequestBody PrestamoRequestDTO prestamoRequestDTO) {
        PrestamoResponseDTO prestamo = prestamoServiceImpl.updatePrestamo(id,prestamoRequestDTO);
        return new ResponseEntity<>(prestamo, HttpStatus.OK);
   }
    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
   @DeleteMapping("/{id}")
   public ResponseEntity<PrestamoResponseDTO> deletePrestamo(@PathVariable int id) {
        prestamoServiceImpl.deletePrestamo(id);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);

   }
}
