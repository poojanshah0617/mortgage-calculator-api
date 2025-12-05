package com.portfolio.mortgage_calculator.controller;

import com.portfolio.mortgage_calculator.model.MortgageRequest;
import com.portfolio.mortgage_calculator.model.MortgageResponse;
import com.portfolio.mortgage_calculator.service.MortgageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/mortgage")
@Tag(name = "Mortgage Calculator", description = "APIs for mortgage payment calculation and analysis")
public class MortgageController {

    private final MortgageService mortgageService;

    public MortgageController(MortgageService mortgageService) {
        this.mortgageService = mortgageService;
    }

    @Operation(summary = "Calculate mortgage payment",
            description = "Calculate monthly payment, total amount paid and total interest paid for mortgage loan.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calculation successful"
                    ,content = @Content(schema = @Schema(implementation = MortgageRequest.class))),
            @ApiResponse(responseCode = "400", description = "Validation failure - Invalid input")
    })
    @PostMapping("/calculate")
    public ResponseEntity<MortgageResponse> calculateMortgage(@Valid @RequestBody MortgageRequest mortgageRequest) {
        MortgageResponse mortgageResponse = mortgageService.calculateMortgage(mortgageRequest);
        return ResponseEntity.ok(mortgageResponse);
    }

    @Operation(summary = "Health check", description = "Check if the api is running or not")
    @ApiResponse(responseCode = "200", description = "API is healthy and running")
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Mortgage calculator API is running");
    }
}
