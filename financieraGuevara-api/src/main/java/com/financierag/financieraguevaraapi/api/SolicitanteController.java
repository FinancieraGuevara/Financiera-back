package com.financierag.financieraguevaraapi.api;

import com.financierag.financieraguevaraapi.model.dto.DetallePrestamoResponseDTO;
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

    @PutMapping("/{id}")
    public ResponseEntity<SolicitanteResponseDTO> updateSolicitante(@PathVariable int id,
                                                                    @RequestBody SolicitanteRequestDTO solicitanteRequestDTO) {
        SolicitanteResponseDTO solicitante = solicitanteServiceImpl.updateSolicitante(id, solicitanteRequestDTO);
        return new ResponseEntity<>(solicitante, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSolicitante(@PathVariable int id) {
        solicitanteServiceImpl.deleteSolicitante(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
