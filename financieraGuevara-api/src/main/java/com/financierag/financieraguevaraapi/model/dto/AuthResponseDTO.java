package com.financierag.financieraguevaraapi.model.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private Integer id;
    private String username;
    private String token;
    private String role;

}
