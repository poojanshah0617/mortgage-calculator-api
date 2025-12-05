package com.portfolio.mortgage_calculator.service;

import com.portfolio.mortgage_calculator.model.MortgageRequest;
import com.portfolio.mortgage_calculator.model.MortgageResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class MortgageService {

    private static final int SCALE = 2;
    private static final int CALCULATION_SCALE = 10;

    public MortgageResponse calculateMortgage(MortgageRequest mortgageRequest) {
        BigDecimal principal = new BigDecimal(mortgageRequest.getLoanAmount().toString());
        BigDecimal monthlyRate = calculateMonthlyRate(mortgageRequest.getAnnualInterestRate());
        int totalPayments = mortgageRequest.getLoanTermYears() * 12;
        BigDecimal monthlyPayment = calculateMonthlyPayment(principal, monthlyRate, totalPayments);
        BigDecimal totalPaid = monthlyPayment.multiply(new BigDecimal(totalPayments)).setScale(SCALE, RoundingMode.HALF_UP);
        BigDecimal totalInterest = totalPaid.subtract(principal);

        return MortgageResponse.builder()
                .loanAmount(principal.setScale(SCALE, RoundingMode.HALF_UP))
                .monthlyPayment(monthlyPayment)
                .totalAmountPaid(totalPaid)
                .totalInterestPaid(totalInterest.setScale(SCALE, RoundingMode.HALF_UP))
                .numberOfPayments(totalPayments)
                .build();
    }

    private BigDecimal calculateMonthlyRate(Double annualRate) {
        BigDecimal annual = new BigDecimal(annualRate.toString());
        BigDecimal decimal = annual.divide(new BigDecimal("100"), CALCULATION_SCALE, RoundingMode.HALF_UP);
        return decimal.divide(new BigDecimal("12"), CALCULATION_SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateMonthlyPayment(BigDecimal principal, BigDecimal monthlyRate, int totalPayments) {
        if (monthlyRate.compareTo(BigDecimal.ZERO) == 0) {
            return principal.divide(new BigDecimal(totalPayments), SCALE, RoundingMode.HALF_UP);
        }
        BigDecimal onePlusRate = BigDecimal.ONE.add(monthlyRate);
        BigDecimal power = onePlusRate.pow(totalPayments);
        BigDecimal numerator = monthlyRate.multiply(power);
        BigDecimal denominator = power.subtract(BigDecimal.ONE);
        BigDecimal payment = principal.multiply(numerator).divide(denominator, CALCULATION_SCALE, RoundingMode.HALF_UP);
        return payment.setScale(SCALE, RoundingMode.HALF_UP);
    }

}
