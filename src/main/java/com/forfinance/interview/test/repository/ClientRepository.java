package com.forfinance.interview.test.repository;

import org.springframework.data.repository.CrudRepository;

import com.forfinance.interview.test.domain.Client;

/**
 * Repository for {@link Client} instances.
 * 
 * @author Jan Koren
 *
 */
public interface ClientRepository extends CrudRepository<Client, Long> {

}
