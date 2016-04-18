package com.forfinance.interview.test.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forfinance.interview.test.domain.Client;
import com.forfinance.interview.test.domain.Loan;
import com.forfinance.interview.test.dto.LoanApplication;
import com.forfinance.interview.test.repository.ClientRepository;
import com.forfinance.interview.test.repository.LoanRepository;
import com.forfinance.interview.test.service.api.ILoanService;

/**
 * Implementation of {@link ILoanService}.
 * @author Jan Koren
 *
 */
@Service
public class LoanService implements ILoanService {
    
    private static final Logger log = LoggerFactory.getLogger(LoanService.class);
	
	@Autowired
	private LoanRepository loanRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private HttpServletRequest request;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Loan createLoan(LoanApplication loanApplication) {
		return loanRepository.save(toLoan(loanApplication));
	}
	
	private Loan toLoan(LoanApplication loanApplication) {
	    Client client = clientRepository.findOne(loanApplication.getClientId());
	    if (client == null) {
	        client = createClient(loanApplication);
	    }
	    
		Loan loan = new Loan();
		loan.setAmount(loanApplication.getAmount());
		loan.setTerm(loanApplication.getTerm());
		loan.setDateCreated(loanApplication.getDateCreated());
		loan.setIpAddress(request.getRemoteAddr());
        loan.setClient(client);
        
		return loan;
	}

    private Client createClient(LoanApplication loanApplication) {
        Client client = new Client();
		client.setClientId(loanApplication.getClientId());
		client.setFirstName(loanApplication.getFirstName());
		client.setSurname(loanApplication.getSurname());
        return client;
    }

}
