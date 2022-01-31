package com.pwr.psi.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FilterOptionsDto {
    private List<UserDto> supervisors;
    private List<String> thesisTypes;
    private List<String> years;
    private List<String> fieldNames;
    private List<String> languages;
}
