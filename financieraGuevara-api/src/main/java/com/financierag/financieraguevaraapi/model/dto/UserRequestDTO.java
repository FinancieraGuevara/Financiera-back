package com.financierag.financieraguevaraapi.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;
@Data
public class UserRequestDTO {
    @NotBlank(message = "El nombre de usuario no puede estar vacio")
    private String username;
    @NotBlank(message = "Ingrese la contraseña")
    private String password;
    @NotBlank (message = "ingrese el rol")
    public String role;
}
