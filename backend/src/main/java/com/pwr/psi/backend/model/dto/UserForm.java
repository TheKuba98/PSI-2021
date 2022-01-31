package com.pwr.psi.backend.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserForm {
    private String username;
    private String password;

//    public UserForm(String username, String password){
//        this.username=username;
//        this.password=password;
//    }
}
