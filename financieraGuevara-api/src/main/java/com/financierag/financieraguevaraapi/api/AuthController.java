package com.financierag.financieraguevaraapi.api;

import com.financierag.financieraguevaraapi.model.dto.AuthResponseDTO;
import com.financierag.financieraguevaraapi.model.dto.LoginDTO;
import com.financierag.financieraguevaraapi.model.dto.SedeRegisterDTO;
import com.financierag.financieraguevaraapi.model.dto.UserProfileDTO;
import com.financierag.financieraguevaraapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register/sede")
    public ResponseEntity<UserProfileDTO> registerSede(@Valid @RequestBody SedeRegisterDTO sedeRegisterDTO){
        UserProfileDTO userProfileDTO = userService.registerSede(sedeRegisterDTO);
        return new ResponseEntity<>(userProfileDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO){
        AuthResponseDTO authResponseDTO = userService.login(loginDTO);
        return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
    }
}
