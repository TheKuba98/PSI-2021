package com.pwr.psi.backend.service.mapper;

import com.pwr.psi.backend.model.dto.UserDto;
import com.pwr.psi.backend.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDto userToUserDTO(User user) {
        return new UserDto(user.getUsername(), user.getRoles(), user.getFirstName(), user.getLastName());
    }

    public List<UserDto> userListToUserDtoList(List<User> userList) {
        return userList.stream().map(this::userToUserDTO).collect(Collectors.toList());
    }

    ;

}
