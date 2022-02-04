package com.pwr.psi.backend.model.enums;

import java.util.List;

public enum ThesisType {
    MASTERS,
    BSC,
    BA;

    public static List<ThesisType> getAllThesisTypes(){
        return List.of(MASTERS, BSC, BA);
    }
}
