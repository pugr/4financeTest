package com.forfinance.interview.test.repository;

import org.springframework.data.repository.CrudRepository;

import com.forfinance.interview.test.domain.Loan;

/**
 * Repository for {@link Loan} instances.
 * 
 * @author Jan Koren
 *
 */
public interface LoanRepository extends CrudRepository<Loan, Long> {

}
