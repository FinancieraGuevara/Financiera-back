package com.financierag.financieraguevaraapi.api;

import com.financierag.financieraguevaraapi.model.dto.DetallePrestamoResponseDTO;
import com.financierag.financieraguevaraapi.service.impl.DetallePrestamoServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/private/detalleprestamos")
@AllArgsConstructor

public class DetallePrestamoController {

    private final DetallePrestamoServiceImpl detallePrestamoServiceImpl;

    @GetMapping
    public ResponseEntity<List<DetallePrestamoResponseDTO>> getAllDetallePrestamos(){
        List<DetallePrestamoResponseDTO> detallePrestamos = detallePrestamoServiceImpl.findAllDetallesPrestamo();
        return new ResponseEntity<>(detallePrestamos, HttpStatus.OK);
    }

    @GetMapping("/prestamoId/{id}")
    public  ResponseEntity<DetallePrestamoResponseDTO> getDetallePrestamoById(@PathVariable int id){
        DetallePrestamoResponseDTO detallePrestamos = detallePrestamoServiceImpl.findDetallePrestamoById(id);
        return new ResponseEntity<>(detallePrestamos, HttpStatus.OK);
    }

    @GetMapping("/{solicitanteId}")
    public ResponseEntity<List<DetallePrestamoResponseDTO>> getDetalleSolicitanteById(@PathVariable int solicitanteId){
        List<DetallePrestamoResponseDTO> detallePrestamo = detallePrestamoServiceImpl.detallePrestamoSolicitante(solicitanteId);
        return new ResponseEntity<>(detallePrestamo, HttpStatus.OK);
    }


    @PutMapping("/skip/16months")
    public ResponseEntity<String> SkipSixteenMonths()
    {
        detallePrestamoServiceImpl.sixteenmonths();
        return new ResponseEntity<>("Adelantando 16 meses", HttpStatus.OK);
    }

    @PutMapping("/skip/2years")
    public ResponseEntity<String> SkipTwoYears()
    {
        detallePrestamoServiceImpl.twoyear();
        return new ResponseEntity<>("Adelantando 2 años", HttpStatus.OK);
    }
    @PutMapping("/skip/{number}/months")
    public ResponseEntity<String> SkipCustomMonths(@PathVariable int number)
    {   LocalDate today = LocalDate.now().plusMonths(number);
        detallePrestamoServiceImpl.customMonths(number);
        return new ResponseEntity<>("Adelantando " +number +" meses"+"\nFecha : "+today, HttpStatus.OK);
    }
}
