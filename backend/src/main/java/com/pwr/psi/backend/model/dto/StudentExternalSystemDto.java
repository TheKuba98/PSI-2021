package com.pwr.psi.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class StudentExternalSystemDto {
    private String indexNumber;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private List<FieldExternalDto> fields;

    public StudentExternalSystemDto() {
    }
}
