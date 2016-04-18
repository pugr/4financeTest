package com.forfinance.interview.test.service.api;

import com.forfinance.interview.test.domain.RiskClassification;
import com.forfinance.interview.test.dto.LoanApplication;

/**
 * The service whose responsibility is to perform loan application risk analysis.
 * 
 * @author Jan Koren
 *
 */
public interface ILoanRiskAnalysisService {

    /**
     * Performs risk analysis for a given loan application.
     * 
     * @param application
     *            The {@link LoanApplication} instance.
     * @return {@link RiskClassification#HIGH} if the application is analyzed risky, otherwise {@link RiskClassification#LOW}.
     */
    RiskClassification performRiskAnalysis(LoanApplication application);
}
