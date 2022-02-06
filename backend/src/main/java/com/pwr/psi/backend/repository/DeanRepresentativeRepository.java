package com.pwr.psi.backend.repository;

import com.pwr.psi.backend.model.entity.DeanRepresentative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeanRepresentativeRepository extends JpaRepository<DeanRepresentative, String> {
    Optional<DeanRepresentative> findByUsername(String username);
}
