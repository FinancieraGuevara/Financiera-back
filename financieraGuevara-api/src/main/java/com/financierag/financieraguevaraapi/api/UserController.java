package com.financierag.financieraguevaraapi.api;


import com.financierag.financieraguevaraapi.model.dto.PasswordDTO;
import com.financierag.financieraguevaraapi.model.dto.UserRequestDTO;
import com.financierag.financieraguevaraapi.model.dto.UserResponseDTO;
import com.financierag.financieraguevaraapi.model.entity.User;
import com.financierag.financieraguevaraapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /*@GetMapping("/users")
    public ResponseEntity<List<User>> getPrivateUsers() {
        return new ResponseEntity<>(userService.getPrivateUsers(), HttpStatus.OK);
    }


    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody UserRequestDTO userRequestDTO, Authentication authentication) {
        // Obtiene los roles del usuario autenticado
        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        // Verifica si el usuario tiene el rol ADMIN
        if (!isAdmin) {
            return new ResponseEntity<>("Acceso denegado", HttpStatus.FORBIDDEN);
        }
        // Continúa con la lógica de registro
        userService.addUser(userRequestDTO);
        return new ResponseEntity<>(userRequestDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        // Verifica si el usuario tiene el rol ADMIN
        if (!isAdmin) {
            return new ResponseEntity<>("Acceso denegado", HttpStatus.FORBIDDEN);
        }
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }*/

    @PutMapping("/edit-password")
    public ResponseEntity<?> editPassword(@Valid @RequestBody PasswordDTO passwordDTO){
        try {
            userService.updatePassword(passwordDTO);
            return ResponseEntity.ok().body("Contraseña actualizada correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
