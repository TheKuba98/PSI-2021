package com.pwr.psi.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ThesisSearchDto {
    String thema;
    String supervisor;
    String type;
    String year;
    String fieldName;
    String language;
}
