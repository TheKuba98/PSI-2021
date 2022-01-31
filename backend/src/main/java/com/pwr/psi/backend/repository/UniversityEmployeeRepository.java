package com.pwr.psi.backend.repository;

import com.pwr.psi.backend.model.entity.UniversityEmployee;
import com.pwr.psi.backend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniversityEmployeeRepository extends JpaRepository<UniversityEmployee, String> {
}
