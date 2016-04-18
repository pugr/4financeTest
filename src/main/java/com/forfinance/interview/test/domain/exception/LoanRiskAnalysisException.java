package com.forfinance.interview.test.domain.exception;

/**
 * The domain-specific exception that should be thrown in case the loan
 * application risk analysis is unsuccessful for some reason.
 * 
 * @author Jan Koren
 *
 */
public class LoanRiskAnalysisException extends RuntimeException {

    private static final long serialVersionUID = -5006068873378283923L;

    public LoanRiskAnalysisException(String message) {
        super(message);
    }

}
