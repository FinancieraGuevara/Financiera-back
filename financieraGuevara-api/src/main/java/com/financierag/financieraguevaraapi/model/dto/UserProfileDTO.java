package com.financierag.financieraguevaraapi.model.dto;

import com.financierag.financieraguevaraapi.model.entity.Role;
import lombok.Data;

@Data
public class UserProfileDTO {

    private Integer id;
    private String username;
    private Role role; //Puedes ser OWNER o SEDE

    private String name;
    private String address;

}
