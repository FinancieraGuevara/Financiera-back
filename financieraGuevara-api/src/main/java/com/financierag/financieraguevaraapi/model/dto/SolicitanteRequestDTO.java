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
    @Size(max = 155, message = "El nombre no puede exceder los 35 caracteres")
    private String nombre_completo;
}
