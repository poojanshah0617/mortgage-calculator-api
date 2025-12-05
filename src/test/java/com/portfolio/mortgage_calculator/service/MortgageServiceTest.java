package com.portfolio.mortgage_calculator.service;

import com.portfolio.mortgage_calculator.model.MortgageRequest;
import com.portfolio.mortgage_calculator.model.MortgageResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class MortgageServiceTest {
private final MortgageService service = new MortgageService();

@Test
void testCalculateMortgage_StandardLoan(){
    MortgageRequest request= new MortgageRequest();
    request.setLoanAmount(350000.0);
    request.setLoanTermYears(30);
    request.setAnnualInterestRate(6.25);

    MortgageResponse response = service.calculateMortgage(request);

    assertNotNull(response);
    assertEquals(new BigDecimal("350000.00"),response.getLoanAmount());
    assertEquals(0, new BigDecimal("2155.01").compareTo(response.getMonthlyPayment()));
    assertEquals(360, response.getNumberOfPayments());
    assertTrue(new BigDecimal("775803.60").compareTo(response.getTotalAmountPaid())==0);
    assertTrue(new BigDecimal("425803.60").compareTo(response.getTotalInterestPaid())==0);
}
}
