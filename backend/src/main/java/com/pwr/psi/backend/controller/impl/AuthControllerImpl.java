package com.pwr.psi.backend.controller.impl;

import com.pwr.psi.backend.exception.UserNotFoundException;
import com.pwr.psi.backend.controller.api.AuthController;
import com.pwr.psi.backend.model.dto.UserDto;
import com.pwr.psi.backend.model.dto.UserForm;
import com.pwr.psi.backend.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthControllerImpl implements AuthController {

    private final UserService userService;

    @Override
    public ResponseEntity<UserDto> login(UserForm userForm) throws UserNotFoundException {
        UserDto foundUser = userService.getUserByUsername(userForm.getUsername(), userForm.getPassword());
        return ResponseEntity.ok(foundUser);
    }

    @Override
    public String getTestUser() {
        return "Test dostęp dla Usera";
    }

    @Override
    public String getTestAdmin() {
        return "Test dostęp dla Admina";
    }

    //...
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<?> handleException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
