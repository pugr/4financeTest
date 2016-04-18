package com.forfinance.interview.test.util;

import java.time.LocalDateTime;
import java.time.Month;

import com.forfinance.interview.test.domain.Loan;
import com.forfinance.interview.test.dto.LoanApplication;

public class LoanTestUtils {
    
    public static final long CLIENT_ID = 123456L;
    public static final String FIRST_NAME = "John";
    public static final String SURNAME = "Doe";
    public static final long AMOUNT = 1_000_000;
    public static final int TERM = 1_000_000;
    public static final long LOAN_MAXIMUM_AMOUNT = 10_000_000L;
    public static final String IP_ADDRESSS = "127.0.0.1";
    public static final LocalDateTime LOAN_DATE_OUTSIDE_CRITICAL_HOUR_RANGE = LocalDateTime.of(2016, Month.APRIL, 17, 10, 00);
    public static final LocalDateTime LOAN_DATE_WITHIN_CRITICAL_HOUR_RANGE = LocalDateTime.of(2016, Month.APRIL, 17, 3, 00);
    
    public static Loan createLoan(long amount, String ipAddress, LocalDateTime dateCreated) {
        Loan loan = new Loan();
        loan.setAmount(amount);
        loan.setIpAddress(ipAddress);
        loan.setDateCreated(dateCreated);
        return loan;
    }

    public static LoanApplication createLoanApplication(long clientId, long amount, int term, LocalDateTime dateCreated) {
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setClientId(clientId);
        loanApplication.setAmount(amount);
        loanApplication.setTerm(term);
        loanApplication.setDateCreated(dateCreated);
        return loanApplication;
    }

}
