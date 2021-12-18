package com.sefa.accounting.exception;

public class SpecialistNotFoundException extends RuntimeException {
    public SpecialistNotFoundException() {
        super("Specialist not found");
    }

    public SpecialistNotFoundException(String id) {
        super("Specialist not found with given id : " + id);
    }
}
