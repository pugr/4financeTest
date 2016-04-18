package com.forfinance.interview.test.facade.impl;

import static com.forfinance.interview.test.util.LoanTestUtils.AMOUNT;
import static com.forfinance.interview.test.util.LoanTestUtils.CLIENT_ID;
import static com.forfinance.interview.test.util.LoanTestUtils.LOAN_DATE_WITHIN_CRITICAL_HOUR_RANGE;
import static com.forfinance.interview.test.util.LoanTestUtils.LOAN_MAXIMUM_AMOUNT;
import static com.forfinance.interview.test.util.LoanTestUtils.TERM;
import static com.forfinance.interview.test.util.LoanTestUtils.createLoanApplication;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.forfinance.interview.test.domain.Loan;
import com.forfinance.interview.test.domain.RiskClassification;
import com.forfinance.interview.test.domain.exception.LoanRiskAnalysisException;
import com.forfinance.interview.test.dto.LoanApplication;
import com.forfinance.interview.test.service.api.ILoanRiskAnalysisService;
import com.forfinance.interview.test.service.api.ILoanService;
import com.forfinance.interview.test.util.LoanTestUtils;

/**
 * Test suite for {@link LoanFacade}.
 * 
 * @author Jan Koren
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class LoanFacadeTest {

    @InjectMocks
    private LoanFacade loanFacade;

    @Mock
    private ILoanRiskAnalysisService loanRiskAnalysisService;
    @Mock
    private ILoanService loanService;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(loanFacade, "maximumLoanAmount", LOAN_MAXIMUM_AMOUNT);
    }

    @Test(expected = IllegalStateException.class)
    public void applyForLoanAmountGreaterThanDefinedMaximum() {
        LoanApplication loanApplication = createLoanApplication(CLIENT_ID, Long.MAX_VALUE, TERM, LOAN_DATE_WITHIN_CRITICAL_HOUR_RANGE);
        loanFacade.applyForLoan(loanApplication);
    }

    @Test(expected = LoanRiskAnalysisException.class)
    public void applyForLoanApplicationRejected() {
        LoanApplication loanApplication = createLoanApplication(CLIENT_ID, AMOUNT, TERM, LOAN_DATE_WITHIN_CRITICAL_HOUR_RANGE);
        when(loanRiskAnalysisService.performRiskAnalysis(eq(loanApplication))).thenReturn(RiskClassification.HIGH);
        loanFacade.applyForLoan(loanApplication);
    }

    @Test
    public void applyForLoanApplicationPassed() {
        LoanApplication loanApplication = createLoanApplication(CLIENT_ID, AMOUNT, TERM, LOAN_DATE_WITHIN_CRITICAL_HOUR_RANGE);
        when(loanRiskAnalysisService.performRiskAnalysis(eq(loanApplication))).thenReturn(RiskClassification.LOW);
        Loan loan = new Loan();
        when(loanService.createLoan(eq(loanApplication))).thenReturn(loan);
        Loan createdLoan = loanFacade.applyForLoan(loanApplication);
        assertEquals(loan, createdLoan);
    }

}
