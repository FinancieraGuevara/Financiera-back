package com.financierag.financieraguevaraapi.service;

import com.financierag.financieraguevaraapi.model.dto.UserRequestDTO;
import com.financierag.financieraguevaraapi.model.dto.UserResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public List<UserResponseDTO> getAllUsers();
    public void addUser(UserRequestDTO user);
    public void deleteUser(int userId);
    public boolean login(UserRequestDTO user);
}
