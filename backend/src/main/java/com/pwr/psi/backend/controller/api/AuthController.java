package com.pwr.psi.backend.controller.api;

import com.pwr.psi.backend.model.dto.UserDto;
import com.pwr.psi.backend.model.dto.UserForm;
import com.pwr.psi.backend.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@CrossOrigin
public interface AuthController {

    @PostMapping("/login")
    ResponseEntity<UserDto> login(@RequestBody UserForm userForm) throws UserNotFoundException;

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/test-student")
    String getTestUser();

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/test-admin")
    String getTestAdmin();
}
