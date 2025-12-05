package com.portfolio.mortgage_calculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.mortgage_calculator.model.MortgageRequest;
import com.portfolio.mortgage_calculator.model.MortgageResponse;
import com.portfolio.mortgage_calculator.service.MortgageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MortgageController.class)
public class MortgageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MortgageService mortgageService;

    @Test
    void testHealthEndPoint() throws Exception{
        mockMvc.perform(get("/api/v1/mortgage/health")).andExpect(status().isOk())
                .andExpect(content().string("Mortgage calculator API is running"));
    }

    @Test
    void testCalculateMortgage_Success() throws Exception{
        MortgageRequest mortgageRequest= new MortgageRequest();
        mortgageRequest.setLoanAmount(350000.0);
        mortgageRequest.setLoanTermYears(30);
        mortgageRequest.setAnnualInterestRate(6.25);

        MortgageResponse mortgageResponse= MortgageResponse.builder()
                .loanAmount(new BigDecimal("350000.00"))
                .monthlyPayment(new BigDecimal("2155.01"))
                .totalAmountPaid(new BigDecimal("775803.60"))
                .totalInterestPaid(new BigDecimal("425803.60"))
                .numberOfPayments(360).build();
        when(mortgageService.calculateMortgage(any())).thenReturn(mortgageResponse);

        mockMvc.perform(post("/api/v1/mortgage/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mortgageRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loanAmount").value(350000.00))
                .andExpect(jsonPath("$.monthlyPayment").value(2155.01))
                .andExpect(jsonPath("$.totalAmountPaid").value(775803.60))
                .andExpect(jsonPath("$.totalInterestPaid").value(425803.60))
                .andExpect(jsonPath("$.numberOfPayments").value(360));
    }

    @Test
    void testCalculateMortgage_Failure() throws Exception{
        MortgageRequest mortgageRequest= new MortgageRequest();
        mortgageRequest.setLoanTermYears(30);
        mortgageRequest.setAnnualInterestRate(6.25);

        mockMvc.perform(post("/api/v1/mortgage/calculate").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mortgageRequest))).andExpect(status().isBadRequest());
    }

}
