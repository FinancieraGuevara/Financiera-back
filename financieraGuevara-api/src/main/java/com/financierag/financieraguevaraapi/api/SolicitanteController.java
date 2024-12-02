package com.financierag.financieraguevaraapi.api;

import com.financierag.financieraguevaraapi.model.dto.DetallePrestamoResponseDTO;
import com.financierag.financieraguevaraapi.model.dto.DeudoresDTO;
import com.financierag.financieraguevaraapi.model.dto.SolicitanteRequestDTO;
import com.financierag.financieraguevaraapi.model.dto.SolicitanteResponseDTO;
import com.financierag.financieraguevaraapi.model.entity.DetallePrestamo;
import com.financierag.financieraguevaraapi.model.entity.Solicitante;
import com.financierag.financieraguevaraapi.repository.SolicitanteRepository;
import com.financierag.financieraguevaraapi.service.impl.DetallePrestamoServiceImpl;
import com.financierag.financieraguevaraapi.service.impl.SolicitanteServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/solicitantes")
@AllArgsConstructor

public class SolicitanteController {

    private final SolicitanteServiceImpl solicitanteServiceImpl;

    @GetMapping
    public ResponseEntity<List<SolicitanteResponseDTO>> findAll() {
        List<SolicitanteResponseDTO> solicitante = solicitanteServiceImpl.getAllSolicitantes();
        return new ResponseEntity<>(solicitante, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitanteResponseDTO> findById(@PathVariable int id) {
        SolicitanteResponseDTO solicitante = solicitanteServiceImpl.getSolicitanteById(id);
        return new ResponseEntity<>(solicitante, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSolicitante(@PathVariable int id) {
        solicitanteServiceImpl.deleteSolicitante(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/searchByDni/{numero}")
    public ResponseEntity<Integer> findSolicitanteIdByNumero(@PathVariable String numero) {
        Optional<Integer> solicitanteId = solicitanteServiceImpl.findSolicitanteIdByNumero(numero);
        if (solicitanteId.isPresent()) {
            return new ResponseEntity<>(solicitanteId.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/deudores")
    public ResponseEntity<List<SolicitanteResponseDTO>> findDeudores() {
        List<SolicitanteResponseDTO> solicitantes = solicitanteServiceImpl.obtenerDeudores();
        return new ResponseEntity<>(solicitantes, HttpStatus.OK);
    }

    @GetMapping("/deudoresYDinero")
    public List<DeudoresDTO> findDeudoresAndMoney() {
        return solicitanteServiceImpl.obtenerDeudoresYDinero();
    }
}
