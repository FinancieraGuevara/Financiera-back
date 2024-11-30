package com.financierag.financieraguevaraapi.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SedeRegisterDTO {

    @NotBlank(message = "El nombre de la sede es obligatorio")
    private String name;

    @NotBlank(message = "La dirección de la sede es obligatoria")
    private String address;

    @NotBlank(message = "El username es obligatorio")
    private String username;

    @NotBlank(message = "El password es obligatorio")
    @Size(min = 8, max = 20, message = "La contraseña debe tener entre 8 y 20 caracteres")
    private String password;

}
