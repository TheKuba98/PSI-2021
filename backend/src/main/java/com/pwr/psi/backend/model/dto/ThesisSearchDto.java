package com.pwr.psi.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThesisSearchDto {
    String theme;
    String supervisor;
    String type;
    String year;
    String fieldName;
    String language;
}
