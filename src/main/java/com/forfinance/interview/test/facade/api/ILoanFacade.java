package com.forfinance.interview.test.facade.api;

import com.forfinance.interview.test.domain.Loan;
import com.forfinance.interview.test.dto.LoanApplication;

/**
 * The facade class orchestrating possible multiple service calls and executing them in a single transaction.
 * 
 * @author Jan Koren
 *
 */
public interface ILoanFacade {

    /**
     * Applies for a loan.
     * 
     * @param loanApplication
     *            The {@link LoanApplication} instance holding input data.
     * @return The created {@link Loan} instance or {@link LoanRiskAnalysisException} if the loan application is rejected.
     */
    Loan applyForLoan(LoanApplication loanApplication);

}
