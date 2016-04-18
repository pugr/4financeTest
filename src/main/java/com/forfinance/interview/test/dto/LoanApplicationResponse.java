package com.forfinance.interview.test.dto;

import java.time.LocalDateTime;

/**
 * Represents loan application data coming as the HTTP response from the endpoint back to the client.
 * 
 * @author Jan Koren
 *
 */
public class LoanApplicationResponse extends LoanApplicationBaseDto {

    private long loanId;
    private LocalDateTime dateCreated;

    public long getLoanId() {
        return loanId;
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

}
