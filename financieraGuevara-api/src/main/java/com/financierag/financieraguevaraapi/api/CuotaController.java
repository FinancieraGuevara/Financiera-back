package com.financierag.financieraguevaraapi.api;

import com.financierag.financieraguevaraapi.model.dto.PrestamoResponseDTO;
import com.financierag.financieraguevaraapi.service.CuotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pagar")
@RequiredArgsConstructor
public class CuotaController {
    private final CuotaService cuotaService;
    @PutMapping("/prestamo/{idPrestamo}/cuota/{nmrcuota}")
    public PrestamoResponseDTO setCoutaPayet(@PathVariable int idPrestamo, @PathVariable int nmrcuota )
    {
        return cuotaService.SetCoutaPayed(idPrestamo,nmrcuota);
    }

}
