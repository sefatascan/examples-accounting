package com.sefa.accounting.service;

import com.sefa.accounting.dto.request.SpecialistRequest;
import com.sefa.accounting.dto.response.SpecialistDetailResponse;
import com.sefa.accounting.exception.SpecialistNotFoundException;
import com.sefa.accounting.model.Specialist;
import com.sefa.accounting.repository.SpecialistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SpecialistServiceTest {

    @InjectMocks
    private SpecialistService specialistService;

    @Mock
    private SpecialistRepository specialistRepository;


    @BeforeEach
    void setUp() {
        Specialist specialist = Specialist.builder()
                .id("specialist-id")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@gmail.com")
                .build();
        Mockito.when(specialistRepository.findById("specialist-id"))
                .thenReturn(Optional.of(specialist));

        Mockito.when(specialistRepository
                .findSpecialistsByFirstNameAndLastNameAndEmail("John", "Doe", "john.doe@gmail.com"))
                .thenReturn(Optional.of(specialist));
    }

    @Test
    void findById() {
        Specialist expected = specialistService.findById("specialist-id");

        assertThat(expected).isNotNull();
        assertThat(expected).hasFieldOrPropertyWithValue("id", "specialist-id");
    }

    @Test
    void whenNotExistThrowException_findById() {
        assertThrows(SpecialistNotFoundException.class, () -> specialistService.findById("test1"));
    }

    @Test
    void findByUniqueConstraint() {
        Specialist expected = specialistService.findByUniqueConstraint("John", "Doe", "john.doe@gmail.com");

        assertThat(expected).isNotNull();
        assertThat(expected).hasFieldOrPropertyWithValue("firstName", "John");
        assertThat(expected).hasFieldOrPropertyWithValue("lastName", "Doe");
        assertThat(expected).hasFieldOrPropertyWithValue("email", "john.doe@gmail.com");
    }

    @Test
    void whenNotExistThrowException_findByUniqueConstraint() {
        assertThrows(SpecialistNotFoundException.class, () -> specialistService
                .findByUniqueConstraint("testFirsName", "testLastName", "testEmail"));
    }

    @Test
    void createSpecialist() {
        SpecialistRequest request = new SpecialistRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john.doe@gmail.com");

        Specialist specialist = Specialist.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@gmail.com")
                .build();

        Mockito.when(specialistRepository.save(specialist))
                .thenReturn(specialist);

        SpecialistDetailResponse expected = specialistService.createSpecialist(request);

        assertThat(expected).isNotNull();
        assertThat(expected).hasFieldOrPropertyWithValue("firstName", "John");
        assertThat(expected).hasFieldOrPropertyWithValue("lastName", "Doe");
        assertThat(expected).hasFieldOrPropertyWithValue("email", "john.doe@gmail.com");

    }
}