package com.pwr.psi.backend.service;

import com.pwr.psi.backend.controller.UserNotFoundException;
import com.pwr.psi.backend.model.dto.UserDto;
import com.pwr.psi.backend.model.entity.User;
import com.pwr.psi.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserDto getUserByUsername(String username, String password) throws UserNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User with this login does not exsit"));
        if (!passwordEncoder.matches(password, user.getPassword())) throw new UserNotFoundException("Incorrect password");
        return new UserDto(user.getUsername(), user.getRoles(), user.getFirstName(), user.getLastName());
    }
}

