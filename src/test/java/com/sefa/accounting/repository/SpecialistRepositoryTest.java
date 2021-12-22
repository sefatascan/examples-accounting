package com.sefa.accounting.repository;

import com.sefa.accounting.model.Specialist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SpecialistRepositoryTest extends EntityManagerHelper {

    @Autowired
    private SpecialistRepository specialistRepository;

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(specialistRepository).isNotNull();
    }

    @Test
    void findSpecialistsByFirstNameAndLastNameAndEmail() {
        Specialist specialist = createSpecialist();

        Optional<Specialist> expected = specialistRepository.findSpecialistsByFirstNameAndLastNameAndEmail(
                specialist.getFirstName(),
                specialist.getLastName(),
                specialist.getEmail()
        );
        assertThat(expected).isPresent();
        assertThat(expected.get()).isEqualTo(specialist);
    }

    @Test
    void notFindSpecialistsByFirstNameAndLastNameAndEmail() {
        Optional<Specialist> expected = specialistRepository.findSpecialistsByFirstNameAndLastNameAndEmail(
                "John",
                "Doe",
                "john.doe@gmail.com"
        );
        assertThat(expected).isNotPresent();
    }
}