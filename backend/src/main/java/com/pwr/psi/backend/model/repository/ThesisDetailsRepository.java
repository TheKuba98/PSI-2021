package com.pwr.psi.backend.model.repository;

import com.pwr.psi.backend.model.entity.ThesisDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThesisDetailsRepository extends JpaRepository<ThesisDetails, Integer> {
}
