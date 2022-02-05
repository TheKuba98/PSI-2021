package com.pwr.psi.backend.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ThesisForm {
    private String theme;
    private String supervisor;
    private String type;
    private String field;
    private String year;
    private String language;
}
