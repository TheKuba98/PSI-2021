package com.pwr.psi.backend.service.api;

import com.pwr.psi.backend.exception.UserNotFoundException;
import com.pwr.psi.backend.model.dto.UserDto;

public interface UserService {
    UserDto getUserByUsername(String username, String password) throws UserNotFoundException;
}
