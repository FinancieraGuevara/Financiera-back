package com.financierag.financieraguevaraapi.service.impl;

import com.financierag.financieraguevaraapi.mapper.UserMapper;
import com.financierag.financieraguevaraapi.model.dto.UserRequestDTO;
import com.financierag.financieraguevaraapi.model.dto.UserResponseDTO;
import com.financierag.financieraguevaraapi.model.dto.UserResponsePrivateDTO;
import com.financierag.financieraguevaraapi.model.entity.User;
import com.financierag.financieraguevaraapi.repository.UserRepository;
import com.financierag.financieraguevaraapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.Mapping;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<UserResponseDTO> getAllUsers() {
        if(userMapper.converToListDTO(userRepository.findAll())!=null)
        {
            return userMapper.converToListDTO(userRepository.findAll());
        }
        else
        {
            throw  new NullPointerException("Error no se encontraron usuarios");
        }
    }

    @Override
    public void addUser(UserRequestDTO user) {
       User euser= userMapper.convertToEntity(user);
       String usernameprovided = euser.getUsername();
       if(userRepository.findByUsername(usernameprovided)!=null)
       {
           throw  new IllegalArgumentException("Error el usuario ya existe");
       }
        userRepository.save(euser);
    }

    @Override
    public void deleteUser(int userId) {
        User user= userRepository.findById(userId).orElse(null);
        if (user != null) {
            userRepository.delete(user);
        }
        else {
            throw  new NullPointerException("Error el usuario no existe");
        }
    }

    @Override
    public boolean login(UserRequestDTO user) {
        String usernameprovided = user.getUsername();
        String passwordprovided = user.getPassword();

        User user1 = userRepository.findByUsername(usernameprovided);

        if (user1==null) {
            return false;
        }

        if (user1.getPassword().equals(passwordprovided)) {
            return true;
        }

        return false;
    }

    @Override
    public List<User> getPrivateUsers() {
        return userRepository.findAll();
    }
}
