package com.pwr.psi.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private String username;
    private String roles;
    private String firstName;
    private String lastName;
}
