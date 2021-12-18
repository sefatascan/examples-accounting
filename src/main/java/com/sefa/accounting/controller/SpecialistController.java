package com.sefa.accounting.controller;

import com.sefa.accounting.dto.request.SpecialistRequest;
import com.sefa.accounting.dto.response.SpecialistDetailResponse;
import com.sefa.accounting.service.SpecialistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/v1/specialist")
@RequiredArgsConstructor
@Tag(name = "v1/specialist", description = "Endpoints for managing specialists")
public class SpecialistController {
    private final SpecialistService specialistService;

    @Operation(summary = "Create a specialist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns created specialist with its id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SpecialistDetailResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Data conflict",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<SpecialistDetailResponse> createSpecialist(@Valid @RequestBody SpecialistRequest specialistRequest) {
        return new ResponseEntity(specialistService.createSpecialist(specialistRequest), HttpStatus.OK);
    }
}
