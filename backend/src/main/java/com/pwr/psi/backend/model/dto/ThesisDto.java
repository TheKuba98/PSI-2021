package com.pwr.psi.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ThesisDto {
    private String theme;
    private String supervisorUsername;
    private String supervisorNames;
    private String type;
    private String year;
    private String fieldName;
    private String language;
    private String status;
    private List<UserDto> authors;
    private int thesisId;
    private String reporter;
}
