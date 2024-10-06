package com.financierag.financieraguevaraapi.api;

import com.financierag.financieraguevaraapi.mapper.UserMapper;
import com.financierag.financieraguevaraapi.model.dto.UserResponseDTO;
import com.financierag.financieraguevaraapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/public")
@CrossOrigin(origins = "https://fguevara-guevara.web.app")
public class PublicController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getPublicUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
}
