package com.financierag.financieraguevaraapi.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitanteRequestDTO {

    @NotBlank(message = "El numero de DNI no puede estar vacio")
    @Pattern(regexp = "[0-9]+", message = "El numero de DNI debe contener solo digitos")
    private String numero;

    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(max = 35, message = "El nombre no puede exceder los 35 caracteres")
    private String nombres;

    @NotBlank(message = "El apellido paterno no puede estar vacio")
    @Size(max = 40, message = "El apellido no puede exceder los 40 caracteres")
    private String apellido_paterno;

    @NotBlank(message = "El apellido materno no puede estar vacio")
    @Size(max = 40, message = "El apellido no puede exceder los 40 caracteres")
    private String apellido_materno;

    @NotBlank(message = "El codigo verificacion no puede estar vacio")
    private String codigo_verificacion;

    //@NotBlank(message = "La fecha de nacimiento no puede estar vacia")
    private LocalDate fechaNacimiento;
}
