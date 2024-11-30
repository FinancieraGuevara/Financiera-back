package com.financierag.financieraguevaraapi.service.impl;

import com.financierag.financieraguevaraapi.mapper.UserMapper;
import com.financierag.financieraguevaraapi.model.dto.*;
import com.financierag.financieraguevaraapi.model.entity.Role;
import com.financierag.financieraguevaraapi.model.entity.Sede;
import com.financierag.financieraguevaraapi.model.entity.User;
import com.financierag.financieraguevaraapi.repository.RoleRepository;
import com.financierag.financieraguevaraapi.repository.SedeRepository;
import com.financierag.financieraguevaraapi.repository.UserRepository;
import com.financierag.financieraguevaraapi.security.TokenProvider;
import com.financierag.financieraguevaraapi.security.UserPrincipal;
import com.financierag.financieraguevaraapi.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SedeRepository sedeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;


    @Override
    public UserProfileDTO registerSede(SedeRegisterDTO sedeRegisterDTO) {
        Integer userId = getAuthenticatedUserIdFromJWT();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (user.getRole().getName().equals("ROLE_OWNER")) {
            Role role = roleRepository.findById(2)
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
            return registerSedeWhitRole(sedeRegisterDTO, role);
        } else {
            throw new RuntimeException("El usuario no tiene permisos para registrar una sede");
        }

    }

    @Override
    public AuthResponseDTO login(LoginDTO loginDTO) {
        if (loginDTO.getUsername() == null || loginDTO.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
        );


        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();

        String token = tokenProvider.createAccessToken(authentication);


        return userMapper.toAuthResponseDTO(user, token);
    }

    @Override
    public UserProfileDTO updateSede(Integer id, SedeRegisterDTO sedeRegisterDTO) {
        return null;
    }

    @Override
    public UserProfileDTO getSedeById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return userMapper.toUserProfileDTO(user);
    }

    private UserProfileDTO registerSedeWhitRole(SedeRegisterDTO sedeRegisterDTO, Role role) {
        boolean existsByUsername = userRepository.existsByUsername(sedeRegisterDTO.getUsername());
        boolean existByAddress = sedeRepository.existsByAddress(sedeRegisterDTO.getAddress());
        boolean existByName = sedeRepository.existsByName(sedeRegisterDTO.getName());
        boolean existsByNameAndAddress = sedeRepository.existsByNameAndAddress(sedeRegisterDTO.getName(), sedeRegisterDTO.getAddress());

        if (existsByUsername) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }

        if (existByAddress) {
            throw new IllegalArgumentException("La dirección ya existe");
        }

        if (existByName) {
            throw new IllegalArgumentException("El nombre de la sede ya existe");
        }

        if (existsByNameAndAddress) {
            throw new IllegalArgumentException("El nombre de la sede y la dirección ya existen");
        }

        Role roleFound = roleRepository.findByName(role.getName())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        sedeRegisterDTO.setPassword(passwordEncoder.encode(sedeRegisterDTO.getPassword()));

        User user = userMapper.toUserEntity(sedeRegisterDTO);
        user.setRole(roleFound);

        if(Objects.equals(role.getName(), "ROLE_SEDE")) {
            Sede sede = new Sede();
            sede.setName(sedeRegisterDTO.getName());
            sede.setAddress(sedeRegisterDTO.getAddress());
            sede.setUser(user);
            user.setSede(sede);
        }
        else if(Objects.equals(role.getName(), "ROLE_OWNER")) {}

        User savedUser = userRepository.save(user);
        return userMapper.toUserProfileDTO(savedUser);
    }


    @Override
    public Integer getAuthenticatedUserIdFromJWT() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String token = (String) authentication.getCredentials(); // Obtén el token del objeto de autenticación

            // Extraer el email del token
            Claims claims = tokenProvider.getJwtParser().parseClaimsJws(token).getBody();
            String username = claims.getSubject();


            // Buscar el usuario usando el email
            User user = userRepository.findByUsername(username).orElse(null); // Debes implementar este método en tu UserService
            return user != null ? user.getId() : null;
        }
        return null; // Si no hay autenticación, devuelve null
    }
}
