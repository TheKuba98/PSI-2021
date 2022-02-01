package com.pwr.psi.backend.repository;

import com.pwr.psi.backend.model.entity.Student;
import com.pwr.psi.backend.model.entity.Thesis;
import com.pwr.psi.backend.model.entity.UniversityEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThesisRepository extends JpaRepository<Thesis, Integer> {

    List<Thesis> findAllBySupervisorNotNull();

    List<Thesis> findAllBySupervisorEquals(UniversityEmployee user);

    List<Thesis> findAllByAuthorsContains(Student user);
}
