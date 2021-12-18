package com.sefa.accounting.dto.response;

import com.sefa.accounting.model.Specialist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpecialistDetailResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;

    public static SpecialistDetailResponse mapper(Specialist specialist) {
        return SpecialistDetailResponse.builder()
                .id(specialist.getId())
                .firstName(specialist.getFirstName())
                .lastName(specialist.getLastName())
                .email(specialist.getEmail())
                .build();
    }
}
