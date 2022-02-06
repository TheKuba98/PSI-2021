package com.pwr.psi.backend.model.repository;

import com.pwr.psi.backend.model.entity.Student;
import com.pwr.psi.backend.model.entity.Thesis;
import com.pwr.psi.backend.model.entity.UniversityEmployee;
import com.pwr.psi.backend.model.enums.ThesisStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThesisRepository extends JpaRepository<Thesis, Integer> {

    List<Thesis> findAllBySupervisorNotNullAndRegisteredByStudent(boolean registeredByStudent);

    List<Thesis> findAllBySupervisorEquals(UniversityEmployee user);

    List<Thesis> findAllByAuthorsContains(Student user);

    List<Thesis> findAllByRegisteredByStudent(boolean registeredByStudent);

    List<Thesis> findAllByThesisStatus(ThesisStatus thesisStatus);
}
