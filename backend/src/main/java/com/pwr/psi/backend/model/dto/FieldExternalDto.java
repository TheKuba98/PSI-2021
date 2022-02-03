package com.pwr.psi.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FieldExternalDto {

    private String name;
    private String degree;
    private String faculty;
    private String educationCycle;

    public FieldExternalDto() {
    }
}
