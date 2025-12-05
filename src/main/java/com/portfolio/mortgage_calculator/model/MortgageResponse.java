package com.portfolio.mortgage_calculator.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "Mortgage calculation result")
public class MortgageResponse {
    @Schema(description = "Original Loan amount", example = "3500000.00")
    private BigDecimal loanAmount;
    @Schema(description = "Monthly loan payment", example = "2155.01")
    private BigDecimal monthlyPayment;
    @Schema(description = "Total amount paid over life of loan", example = "775803.60")
    private BigDecimal totalAmountPaid;
    @Schema(description = "Total interest paid over life of loan", example = "4275803.60")
    private BigDecimal totalInterestPaid;
    @Schema(description = "Total number of monthly payment", example = "360")
    private Integer numberOfPayments;
}
