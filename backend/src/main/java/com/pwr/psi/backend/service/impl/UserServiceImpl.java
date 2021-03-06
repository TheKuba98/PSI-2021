package com.pwr.psi.backend.service.impl;

import com.pwr.psi.backend.exception.UserNotFoundException;
import com.pwr.psi.backend.model.dto.UserDto;
import com.pwr.psi.backend.model.entity.User;
import com.pwr.psi.backend.model.repository.UserRepository;
import com.pwr.psi.backend.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public static final String USER_NOT_FOUND_MESSAGE = "User with this login does not exist";

    public UserDto getUserByUsername(String username, String password) throws UserNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));
        if (!passwordEncoder.matches(password, user.getPassword())) throw new UserNotFoundException("Incorrect password");
        return new UserDto(user.getUsername(), user.getRoles(), user.getFirstName(), user.getLastName());
    }
}

