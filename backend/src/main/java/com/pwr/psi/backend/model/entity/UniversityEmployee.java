package com.pwr.psi.backend.model.entity;

import com.pwr.psi.backend.model.enums.EAcademicTitle;
import com.pwr.psi.backend.model.enums.EPosition;
import com.pwr.psi.backend.model.enums.EPositionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
@DiscriminatorValue("ROLE_EMPLOYEE")
public class UniversityEmployee extends User {

    @Enumerated(EnumType.STRING)
    private EPosition position;
    @Enumerated(EnumType.STRING)
    private EPositionType positionType;
    @Enumerated(EnumType.STRING)
    private EAcademicTitle academicTitle;
    private int thesisWorkloadLimit;
    private boolean workloadIncreased;
}
