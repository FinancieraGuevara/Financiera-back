package com.financierag.financieraguevaraapi.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrestamoRequestDTO {

    @NotNull(message = "El monto no puede ser vacio")
    private double monto;

    @NotNull(message = "Las cuotas no pueden ser vacias")
    private int cuotas;

}

