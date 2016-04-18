package com.forfinance.interview.test.service.api;

import com.forfinance.interview.test.domain.Loan;
import com.forfinance.interview.test.dto.LoanApplication;

/**
 * 
 * @author Jan Koren
 *
 */
public interface ILoanService {

    /**
     * Creates a new loan based on the input data.
     * @param loanApplication The {@link LoanApplication} instance holding the loan application data.
     * @return The created loan.
     */
	Loan createLoan(LoanApplication loanApplication);
}
