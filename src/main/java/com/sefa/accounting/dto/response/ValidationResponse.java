package com.sefa.accounting.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class ValidationResponse {
    private String message;
    private List<String> details;

    public static ValidationResponse mapper(MethodArgumentNotValidException exception) {
        Function<FieldError, String> convertor = item -> "[" + item.getField() + "] " + item.getDefaultMessage();
        return ValidationResponse.builder()
                .message("Request body is invalid.")
                .details(exception.getFieldErrors().stream().map(convertor).collect(Collectors.toList()))
                .build();
    }
}
