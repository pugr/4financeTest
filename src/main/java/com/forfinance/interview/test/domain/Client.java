package com.forfinance.interview.test.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Domain object representing a client.
 * 
 * @author Jan Koren
 *
 */
@Entity
@Table(name = "CLIENT")
public class Client {

    @Id
    private Long clientId;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "SURNAME", nullable = false)
    private String surname;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private Set<Loan> loans;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
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

    public Set<Loan> getLoans() {
        return loans;
    }

    public void setLoans(Set<Loan> loans) {
        this.loans = loans;
    }

}
