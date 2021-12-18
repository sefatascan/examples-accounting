package com.sefa.accounting.exception;

import com.sefa.accounting.dto.response.ValidationResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(SpecialistNotFoundException.class)
    public ResponseEntity<String> handleSpecialistNotFoundException(SpecialistNotFoundException exception) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TransactionLimitExceededException.class)
    public ResponseEntity<String> handleTransactionLimitExceededException(TransactionLimitExceededException exception) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ApiResponse(responseCode = "422", description = "Returns unprocessable fields of the request body",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ValidationResponse.class))})
    public ResponseEntity<ValidationResponse> handleValidationException(MethodArgumentNotValidException exception) {
        return new ResponseEntity(ValidationResponse.mapper(exception), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        return new ResponseEntity("Data conflict", HttpStatus.CONFLICT);
    }
}
