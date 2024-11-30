package com.financierag.financieraguevaraapi.mapper;

import com.financierag.financieraguevaraapi.model.dto.*;
import com.financierag.financieraguevaraapi.model.entity.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UserMapper {

    private  final ModelMapper modelMapper;

    public User convertToEntity(UserRequestDTO userRequestDTO) {
        return modelMapper.map(userRequestDTO, User.class);
    }

    public UserResponseDTO converToResponseDto(User user) {
        return modelMapper.map(user, UserResponseDTO.class);
    }

    public UserRequestDTO converToRequestDto(User user) {
        return modelMapper.map(user, UserRequestDTO.class);
    }

    public List<UserResponseDTO> converToListDTO(List<User> users){
        return users.stream().map(this::converToResponseDto).toList();
    }

    public User toUserEntity(SedeRegisterDTO sedeRegisterDTO){
        return modelMapper.map(sedeRegisterDTO, User.class);
    }

    public UserProfileDTO toUserProfileDTO(User user){
        UserProfileDTO userProfileDTO = modelMapper.map(user, UserProfileDTO.class);
        if(user.getSede()!=null){
            userProfileDTO.setName(user.getSede().getName());
            userProfileDTO.setAddress(user.getSede().getAddress());

        }
        return userProfileDTO;
    }

    public User toUserEntityLogin(LoginDTO loginDTO){
        return modelMapper.map(loginDTO, User.class);
    }

    public AuthResponseDTO toAuthResponseDTO(User user, String token){
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setToken(token);

        String userName = (user.getSede()!= null)? user.getSede().getName()
                : (user.getRole().getName().equals("ROLE_OWNER"))? "Owner" : "";

        authResponseDTO.setUsername(userName);
        authResponseDTO.setId(user.getId());
        authResponseDTO.setRole(user.getRole().getName());

        return authResponseDTO;
    }
}
