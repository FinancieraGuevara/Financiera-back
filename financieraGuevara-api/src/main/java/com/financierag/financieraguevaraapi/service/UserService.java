package com.financierag.financieraguevaraapi.service;

import com.financierag.financieraguevaraapi.model.dto.*;
import com.financierag.financieraguevaraapi.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    //public List<UserResponseDTO> getAllUsers();
    //public List<User> getPrivateUsers();
    //public void addUser(UserRequestDTO user);
    //public void deleteUser(int userId);
    //public boolean login(UserRequestDTO user);

    UserProfileDTO registerSede(SedeRegisterDTO sedeRegisterDTO);

    UserProfileDTO updateSede(Integer id, SedeRegisterDTO sedeRegisterDTO);

    UserProfileDTO getSedeById(Integer id);

    AuthResponseDTO login(LoginDTO loginDTO);

    Integer getAuthenticatedUserIdFromJWT();

    String updatePassword(PasswordDTO passwordDTO);


}
