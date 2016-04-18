package com.forfinance.interview.test.dto;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 * The parent of the DTO objects used to retrieve/send loan application data on the REST controller level.
 * 
 * @author Jan Koren
 *
 */
public class LoanApplicationBaseDto {

    private long clientId;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String surname;
    @Range(min = 1)
    private long amount;
    @Range(min = 1)
    private int term;

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }
}
