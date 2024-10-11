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
    private String numero;
    private String nombre_completo;
   /*
    private String dni;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;*/
    //private LocalDate fechaNacimiento;
   /* private String numero;
    */
}
