package com.financierag.financieraguevaraapi.mapper;

import com.financierag.financieraguevaraapi.model.dto.UserRequestDTO;
import com.financierag.financieraguevaraapi.model.dto.UserResponseDTO;
import com.financierag.financieraguevaraapi.model.entity.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    private  ModelMapper modelMapper;

    public User convertToEntity(UserRequestDTO userRequestDTO)
    {
        return modelMapper.map(userRequestDTO, User.class);
    }

    public UserResponseDTO converToResponseDto(User user)
    {
        return modelMapper.map(user, UserResponseDTO.class);
    }
    public UserRequestDTO converToRequestDto(User user)
    {
        return modelMapper.map(user, UserRequestDTO.class);
    }
    public List<UserResponseDTO> converToListDTO(List<User> users){
        return users.stream().map(this::converToResponseDto).toList();
    }

}
