package com.financierag.financieraguevaraapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeudoresDTO {
    private int id;
    private String numero;
    private String nombre_completo;
    private String tipo;
    private double deuda;
}
