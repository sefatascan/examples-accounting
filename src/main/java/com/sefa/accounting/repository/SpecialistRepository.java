package com.sefa.accounting.repository;

import com.sefa.accounting.model.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecialistRepository extends JpaRepository<Specialist, String> {

    Optional<Specialist> findSpecialistsByFirstNameAndLastNameAndEmail(String firstName, String lastName, String email);
}
