package com.financierag.financieraguevaraapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitanteResponseDTO {
    private int id;
    private String dni;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    //private LocalDate fechaNacimiento;

   /* private String numero;
    private String nombres;
    private String apellido_paterno;
    private String apellido_materno;
    private String codigo_verificacion;
    private LocalDate fechaNacimiento;*/

}
