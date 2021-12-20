package com.sefa.accounting.controller;

import com.sefa.accounting.dto.request.TransactionRequest;
import com.sefa.accounting.dto.response.TransactionDetailResponse;
import com.sefa.accounting.model.TransactionStatus;
import com.sefa.accounting.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/transaction")
@RequiredArgsConstructor
@Tag(name = "v1/transaction", description = "Endpoints for managing transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @Operation(summary = "Returns successful transactions with pageable")
    @GetMapping("/successful")
    public ResponseEntity<Page<TransactionDetailResponse>> getSuccessful(
            @RequestParam @Parameter(description = "The size  that how much data will appear of the page") int size,
            @RequestParam @Parameter(description = "The page of list divided by size") int page) {
        return new ResponseEntity(transactionService.getTransactionsByStatus(
                TransactionStatus.SUCCESS,
                PageRequest.of(page, size, Sort.by("createdAt").descending())
        ), HttpStatus.OK);
    }

    @Operation(summary = "Returns failed transactions with pageable")
    @GetMapping("/failed")
    public ResponseEntity<Page<TransactionDetailResponse>> getFailed(
            @RequestParam @Parameter(description = "The size  that how much data will appear of the page") int size,
            @RequestParam @Parameter(description = "The page of list divided by size") int page) {
        return new ResponseEntity(transactionService.getTransactionsByStatus(
                TransactionStatus.FAIL,
                PageRequest.of(page, size, Sort.by("createdAt").descending())
        ), HttpStatus.OK);
    }

    @Operation(summary = "Create a transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Returns if specialist not found",
                    content = @Content),
            @ApiResponse(responseCode = "406", description = "Returns if the transaction exceeds the limit",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Returns if bill no has already been accepted",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<TransactionDetailResponse> createTransaction(@Valid @RequestBody TransactionRequest transactionRequest) {
        return new ResponseEntity(transactionService.createTransaction(transactionRequest), HttpStatus.OK);
    }
}
