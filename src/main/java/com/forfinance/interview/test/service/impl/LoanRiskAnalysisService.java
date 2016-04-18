package com.forfinance.interview.test.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.forfinance.interview.test.domain.Loan;
import com.forfinance.interview.test.domain.RiskClassification;
import com.forfinance.interview.test.dto.LoanApplication;
import com.forfinance.interview.test.repository.LoanRepository;
import com.forfinance.interview.test.service.api.ILoanRiskAnalysisService;

/**
 * Implementation of {@link ILoanRiskAnalysisService}.
 * 
 * @author Jan Koren
 *
 */
@Service
public class LoanRiskAnalysisService implements ILoanRiskAnalysisService {

    Logger log = LoggerFactory.getLogger(LoanRiskAnalysisService.class);

    private static final LocalTime CRITICAL_PERIOD_START = LocalTime.MIDNIGHT;
    private static final LocalTime CRITICAL_PERIOD_END = LocalTime.of(6, 0);
    @Value("${loan.maximumAmount}")
    private long maximumLoanAmount;
    @Value("${loan.maximumDailyApplications}")
    private int maximumDailyApplications;

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    HttpServletRequest httpRequest;

    /**
     * {@inheritDoc}
     */
    @Override
    public RiskClassification performRiskAnalysis(LoanApplication loanApplication) {

        if (isBetweenCriticalHourRange(loanApplication) && isMaximumLoanAmout(loanApplication)) {
            return RiskClassification.HIGH;
        }

        LocalDateTime createdDate = LocalDateTime.now();
        if (isMaximumNumberOfApplicationsReached(createdDate.toLocalDate())) {
            return RiskClassification.HIGH;
        }

        return RiskClassification.LOW;
    }

    /**
     * Checks if the loan application was created between a specified hour range.
     * 
     * @param application
     *            The {@link LoanApplication} to be checked.
     * @return True if is between the start (inclusive) and the end (exclusive) of the critical hour range, otherwise false.
     */
    private boolean isBetweenCriticalHourRange(LoanApplication application) {
        LocalTime applicationTimeCreated = application.getDateCreated().toLocalTime();
        return !applicationTimeCreated.isBefore(CRITICAL_PERIOD_START) && applicationTimeCreated.isBefore(CRITICAL_PERIOD_END);
    }

    /**
     * Checks whether the loan application amount is the maximum loan amount.
     * 
     * @param application
     *            The {@link LoanApplication} to be checked.
     * @return True if the application loan amount is equal to the defined maximum value, otherwise false.
     */
    private boolean isMaximumLoanAmout(LoanApplication application) {
        return maximumLoanAmount == application.getAmount();
    }

    /**
     * Checks whether more than a specified number of applications have been created within the current application date.
     * 
     * @param applicationDate
     *            The creation date of the loan application.
     * @return True if the client reached the maximum number of applications for the same day, otherwise false.
     */
    private boolean isMaximumNumberOfApplicationsReached(LocalDate applicationDate) {
        String ipAddress = httpRequest.getRemoteAddr();
        Iterable<Loan> loanApplications = loanRepository.findAll();
        long foundApplications = StreamSupport.stream(loanApplications.spliterator(), false) //
                .filter(application -> ipAddress.equals(application.getIpAddress())) //
                .filter(application -> applicationDate.equals(application.getDateCreated().toLocalDate())) //
                .count();
        return foundApplications >= maximumDailyApplications;
    }
}
