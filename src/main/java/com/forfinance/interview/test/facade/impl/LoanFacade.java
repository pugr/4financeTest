package com.forfinance.interview.test.facade.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.forfinance.interview.test.domain.Loan;
import com.forfinance.interview.test.domain.RiskClassification;
import com.forfinance.interview.test.domain.exception.LoanRiskAnalysisException;
import com.forfinance.interview.test.dto.LoanApplication;
import com.forfinance.interview.test.facade.api.ILoanFacade;
import com.forfinance.interview.test.service.api.ILoanRiskAnalysisService;
import com.forfinance.interview.test.service.api.ILoanService;

/**
 * 
 * @author Jan Koren
 *
 */
@Transactional
@Component
public class LoanFacade implements ILoanFacade {

    private static final String LOAN_AMOUNT_GREATER_THAN_MAXIMUM = "The loan amount cannot be greater than %s";
    
    @Value("${loan.maximumAmount}")
    private long maximumLoanAmount;

    @Autowired
    private ILoanService loanService;
    @Autowired
    private ILoanRiskAnalysisService loanRiskAnalysisService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Loan applyForLoan(LoanApplication loanApplication) {
        Assert.state(loanApplication.getAmount() <= maximumLoanAmount, String.format(LOAN_AMOUNT_GREATER_THAN_MAXIMUM, maximumLoanAmount));

        RiskClassification riskClassification = loanRiskAnalysisService.performRiskAnalysis(loanApplication);
        if (RiskClassification.HIGH.equals(riskClassification)) {
            throw new LoanRiskAnalysisException("Application rejected");
        }
        return loanService.createLoan(loanApplication);
    }

}
