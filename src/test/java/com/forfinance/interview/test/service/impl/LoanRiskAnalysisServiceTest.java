package com.forfinance.interview.test.service.impl;

import static com.forfinance.interview.test.util.LoanTestUtils.AMOUNT;
import static com.forfinance.interview.test.util.LoanTestUtils.CLIENT_ID;
import static com.forfinance.interview.test.util.LoanTestUtils.IP_ADDRESSS;
import static com.forfinance.interview.test.util.LoanTestUtils.LOAN_DATE_OUTSIDE_CRITICAL_HOUR_RANGE;
import static com.forfinance.interview.test.util.LoanTestUtils.LOAN_DATE_WITHIN_CRITICAL_HOUR_RANGE;
import static com.forfinance.interview.test.util.LoanTestUtils.LOAN_MAXIMUM_AMOUNT;
import static com.forfinance.interview.test.util.LoanTestUtils.TERM;
import static com.forfinance.interview.test.util.LoanTestUtils.createLoan;
import static com.forfinance.interview.test.util.LoanTestUtils.createLoanApplication;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.forfinance.interview.test.domain.Loan;
import com.forfinance.interview.test.domain.RiskClassification;
import com.forfinance.interview.test.dto.LoanApplication;
import com.forfinance.interview.test.repository.LoanRepository;
import com.forfinance.interview.test.util.LoanTestUtils;

/**
 * Test suite for {@link LoanRiskAnalysisService}.
 * 
 * @author Jan Koren
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class LoanRiskAnalysisServiceTest {

    @Before
    public void setup() {
        ReflectionTestUtils.setField(loanRiskAnalysisService, "maximumLoanAmount", LOAN_MAXIMUM_AMOUNT);
        ReflectionTestUtils.setField(loanRiskAnalysisService, "maximumDailyApplications", 3);
    }

    @InjectMocks
    private LoanRiskAnalysisService loanRiskAnalysisService;

    @Mock
    private HttpServletRequest request;
    @Mock
    private LoanRepository loanRepository;

    @Test
    public void performRiskAnalysis() {
        LoanApplication loanApplication = createLoanApplication(CLIENT_ID, AMOUNT, TERM, LOAN_DATE_OUTSIDE_CRITICAL_HOUR_RANGE);

        when(request.getRemoteAddr()).thenReturn(IP_ADDRESSS);
        when(loanRepository.findAll()).thenReturn(Collections.emptyList());

        RiskClassification classification = loanRiskAnalysisService.performRiskAnalysis(loanApplication);

        assertEquals(RiskClassification.LOW, classification);
    }

    @Test
    public void performRiskAnalysisDateWithinCriticalHourRange() {
        LoanApplication loanApplication = createLoanApplication(CLIENT_ID, AMOUNT, TERM, LOAN_DATE_WITHIN_CRITICAL_HOUR_RANGE);

        when(request.getRemoteAddr()).thenReturn(IP_ADDRESSS);
        when(loanRepository.findAll()).thenReturn(Collections.emptyList());

        RiskClassification classification = loanRiskAnalysisService.performRiskAnalysis(loanApplication);

        assertEquals(RiskClassification.LOW, classification);
    }

    @Test
    public void performRiskAnalysisDateOutsideCriticalHourRangeAndMaxAmount() {
        LoanApplication loanApplication = createLoanApplication(CLIENT_ID, AMOUNT, TERM, LOAN_DATE_OUTSIDE_CRITICAL_HOUR_RANGE);

        ReflectionTestUtils.setField(loanRiskAnalysisService, "maximumLoanAmount", AMOUNT);
        when(request.getRemoteAddr()).thenReturn(IP_ADDRESSS);
        when(loanRepository.findAll()).thenReturn(Collections.emptyList());

        RiskClassification classification = loanRiskAnalysisService.performRiskAnalysis(loanApplication);

        assertEquals(RiskClassification.LOW, classification);
    }

    @Test
    public void performRiskAnalysisDateBeginningCriticalHourRangeAndMaxAmount() {
        LoanApplication loanApplication = createLoanApplication(CLIENT_ID, AMOUNT, TERM, LocalDateTime.of(2016, Month.APRIL, 17, 0, 00));

        ReflectionTestUtils.setField(loanRiskAnalysisService, "maximumLoanAmount", AMOUNT);
        when(request.getRemoteAddr()).thenReturn(IP_ADDRESSS);
        when(loanRepository.findAll()).thenReturn(Collections.emptyList());

        RiskClassification classification = loanRiskAnalysisService.performRiskAnalysis(loanApplication);

        assertEquals(RiskClassification.HIGH, classification);
    }

    @Test
    public void performRiskAnalysisDateEndCriticalHourRangeAndMaxAmount() {
        LoanApplication loanApplication = createLoanApplication(CLIENT_ID, AMOUNT, TERM, LocalDateTime.of(2016, Month.APRIL, 17, 6, 00));

        ReflectionTestUtils.setField(loanRiskAnalysisService, "maximumLoanAmount", AMOUNT);
        when(request.getRemoteAddr()).thenReturn(IP_ADDRESSS);
        when(loanRepository.findAll()).thenReturn(Collections.emptyList());

        RiskClassification classification = loanRiskAnalysisService.performRiskAnalysis(loanApplication);

        assertEquals(RiskClassification.LOW, classification);
    }

    @Test
    public void performRiskAnalysisDateWithinCriticalHourRangeAndMaxAmount() {
        LoanApplication loanApplication = createLoanApplication(CLIENT_ID, AMOUNT, TERM, LOAN_DATE_WITHIN_CRITICAL_HOUR_RANGE);

        ReflectionTestUtils.setField(loanRiskAnalysisService, "maximumLoanAmount", AMOUNT);
        when(request.getRemoteAddr()).thenReturn(IP_ADDRESSS);
        when(loanRepository.findAll()).thenReturn(Collections.emptyList());

        RiskClassification classification = loanRiskAnalysisService.performRiskAnalysis(loanApplication);

        assertEquals(RiskClassification.HIGH, classification);
    }

    @Test
    public void performRiskAnalysisNumberOfDailyApplicationsExceeded() {
        LoanApplication loanApplication = createLoanApplication(CLIENT_ID, AMOUNT, TERM, LOAN_DATE_WITHIN_CRITICAL_HOUR_RANGE);

        when(request.getRemoteAddr()).thenReturn(IP_ADDRESSS);
        Loan loan = createLoan(AMOUNT, IP_ADDRESSS, LocalDateTime.now());
        Loan loanFromDifferentIpAddress = createLoan(AMOUNT, "127.0.0.2", LocalDateTime.now());
        when(loanRepository.findAll()).thenReturn(asList(loan, loanFromDifferentIpAddress, loan, loan, loan));

        RiskClassification classification = loanRiskAnalysisService.performRiskAnalysis(loanApplication);

        assertEquals(RiskClassification.HIGH, classification);
    }

}
