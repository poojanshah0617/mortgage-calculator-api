package com.portfolio.mortgage_calculator.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "Mortgage calculator request param")
@Data
public class MortgageRequest {
    @Schema(description = "Total loan amount in dollars", example = "3500000")
    @NotNull(message = "Loan amount is required")
    private Double loanAmount;
    @Schema(description = "Annual interest rate as a percentage", example = "6.25")
    @NotNull(message = "Annual interest rate is required")
    private Double annualInterestRate;
    @Schema(description = "Loan term in years", example = "30")
    @NotNull(message = "Loan term is required")
    private Integer loanTermYears;
}
