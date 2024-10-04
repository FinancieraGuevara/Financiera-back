package com.financierag.financieraguevaraapi.api;

import com.financierag.financieraguevaraapi.config.JwtUtil;
import com.financierag.financieraguevaraapi.mapper.UserMapper;
import com.financierag.financieraguevaraapi.model.dto.UserRequestDTO;
import com.financierag.financieraguevaraapi.model.dto.UserResponseDTO;
import com.financierag.financieraguevaraapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRequestDTO userRequestDTO) {
        if (userService.login(userRequestDTO)) {
            // Generar el JWT
            String token = jwtUtil.generateToken(userRequestDTO.getUsername());
            return ResponseEntity.ok(token); // Retornar el token al frontend
        } else {
            // Retornar error si las credenciales son incorrectas
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    }

    @GetMapping("/public/users")
    public ResponseEntity<List<UserResponseDTO>> getPublicUsers() {
       return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody UserRequestDTO userRequestDTO) {
        userService.addUser(userRequestDTO);

       return new ResponseEntity<>("Usuario creado \n"+userRequestDTO ,HttpStatus.CREATED);
    }


}
