package com.pwr.psi.backend.model.repository;

import com.pwr.psi.backend.model.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FieldRepository extends JpaRepository<Field, Integer> {
    Optional<Field> findByNameAndDegreeAndEducationCycleAndFaculty(String name, String degree, String educationCycle, String faculty);

    Optional<Field> findByDegreeAndEducationCycleAndName(String degree, String educationalCycle, String name);
}
