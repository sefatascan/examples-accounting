package com.sefa.accounting.service;

import com.sefa.accounting.dto.request.SpecialistRequest;
import com.sefa.accounting.dto.response.SpecialistDetailResponse;
import com.sefa.accounting.exception.SpecialistNotFoundException;
import com.sefa.accounting.model.Specialist;
import com.sefa.accounting.repository.SpecialistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpecialistService {
    private final SpecialistRepository specialistRepository;

    protected Specialist findById(String id) {
        return specialistRepository.findById(id).orElseThrow(() -> new SpecialistNotFoundException(id));
    }

    protected Specialist findByUniqueConstraint(String firstName, String lastName, String email) {
        return specialistRepository.findSpecialistsByFirstNameAndLastNameAndEmail(firstName, lastName, email)
                .orElseThrow(() -> new SpecialistNotFoundException());
    }

    public SpecialistDetailResponse createSpecialist(SpecialistRequest specialistRequest) {
        Specialist specialist = Specialist.builder()
                .firstName(specialistRequest.getFirstName())
                .lastName(specialistRequest.getLastName())
                .email(specialistRequest.getEmail())
                .build();

        specialistRepository.save(specialist);

        return SpecialistDetailResponse.mapper(specialist);
    }
}
