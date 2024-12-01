package com.financierag.financieraguevaraapi.api;

import com.financierag.financieraguevaraapi.model.dto.PrestamoRequestDTO;
import com.financierag.financieraguevaraapi.model.dto.PrestamoResponseDTO;
import com.financierag.financieraguevaraapi.service.PrestamoService;
import com.financierag.financieraguevaraapi.service.impl.PrestamoServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestamos")
@AllArgsConstructor
@CrossOrigin(origins = {"https://fguevara-guevara.web.app","http://localhost:4200"}, allowCredentials = "true")
public class PrestamoController {

    @Autowired
    private final PrestamoService prestamoService;

    @CrossOrigin(origins = {"https://fguevara-guevara.web.app","http://localhost:4200"}, allowCredentials = "true")
    @GetMapping
    public ResponseEntity<List<PrestamoResponseDTO>> getAllPrestamos() {
        List<PrestamoResponseDTO> prestamos = prestamoService.findAllPrestamos();
        return new ResponseEntity<>(prestamos, HttpStatus.OK);
    }

    @CrossOrigin(origins = {"https://fguevara-guevara.web.app","http://localhost:4200"}, allowCredentials = "true")
    @GetMapping("/pendientes")
    public ResponseEntity<List<PrestamoResponseDTO>> getPendientPayment()
    {
        List<PrestamoResponseDTO> prestamos = prestamoService.getPrestamosPendientes();
        return new ResponseEntity<>(prestamos, HttpStatus.OK);
    }

    @CrossOrigin(origins = {"https://fguevara-guevara.web.app","http://localhost:4200"}, allowCredentials = "true")
    @GetMapping("/pagados")
    public ResponseEntity<List<PrestamoResponseDTO>> getCompletedPayment()
    {
        List<PrestamoResponseDTO> prestamos = prestamoService.getPrestamosPagados();
        return new ResponseEntity<>(prestamos, HttpStatus.OK);
    }

    @CrossOrigin(origins = {"https://fguevara-guevara.web.app","http://localhost:4200"}, allowCredentials = "true")
    @GetMapping("/judiciales")
    public ResponseEntity<List<PrestamoResponseDTO>> getJudicialPrestamos()
    {
        List<PrestamoResponseDTO> prestamos = prestamoService.getPrestamosJudiciales();
        return new ResponseEntity<>(prestamos, HttpStatus.OK);
    }


    @CrossOrigin(origins = {"https://fguevara-guevara.web.app","http://localhost:4200"}, allowCredentials = "true")
   @GetMapping("/{id}")
   public ResponseEntity<PrestamoResponseDTO> getPrestamoById(@PathVariable int id) {
        PrestamoResponseDTO prestamo = prestamoService.findPrestamoById(id);
        return new ResponseEntity<>(prestamo, HttpStatus.OK);
   }
   @CrossOrigin(origins = {"https://fguevara-guevara.web.app","http://localhost:4200"}, allowCredentials = "true")
   @PostMapping("/crear/{solicitanteId}")
   public ResponseEntity<PrestamoResponseDTO> createPrestamo(@PathVariable int solicitanteId, @RequestBody PrestamoRequestDTO prestamoRequestDTO) {
        PrestamoResponseDTO prestamo = prestamoService.createPrestamo(solicitanteId,prestamoRequestDTO);
        return new ResponseEntity<>(prestamo, HttpStatus.CREATED);
   }
    @CrossOrigin(origins = {"https://fguevara-guevara.web.app","http://localhost:4200"}, allowCredentials = "true")
   @PutMapping("/{id}")
   public ResponseEntity<PrestamoResponseDTO> updatePrestamo(@PathVariable int id,
                                                             @RequestBody PrestamoRequestDTO prestamoRequestDTO) {
        PrestamoResponseDTO prestamo = prestamoService.updatePrestamo(id,prestamoRequestDTO);
        return new ResponseEntity<>(prestamo, HttpStatus.OK);
   }
    @CrossOrigin(origins = {"https://fguevara-guevara.web.app","http://localhost:4200"}, allowCredentials = "true")

    @DeleteMapping("/{id}")
    public ResponseEntity<PrestamoResponseDTO> deletePrestamo(@PathVariable int id) {
        prestamoService.deletePrestamo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @CrossOrigin(origins = {"https://fguevara-guevara.web.app","http://localhost:4200"}, allowCredentials = "true")
    @GetMapping("/cuotas-en-deuda")
    public ResponseEntity<List<PrestamoResponseDTO>> getPrestamosConCuotasEnDeuda() {
        List<PrestamoResponseDTO> prestamos = prestamoService.getPrestamosConCuotasEnDeuda();
        return new ResponseEntity<>(prestamos, HttpStatus.OK);
    }
}
