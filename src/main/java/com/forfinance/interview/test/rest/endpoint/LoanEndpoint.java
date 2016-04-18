package com.forfinance.interview.test.rest.endpoint;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.forfinance.interview.test.domain.Loan;
import com.forfinance.interview.test.dto.LoanApplication;
import com.forfinance.interview.test.dto.LoanApplicationRequestDto;
import com.forfinance.interview.test.dto.LoanApplicationResponse;
import com.forfinance.interview.test.facade.api.ILoanFacade;

/**
 * The REST endpoint for loan-related operations.
 * 
 * @author Jan Koren
 *
 */
@RestController
@RequestMapping("/api/loan")
public class LoanEndpoint {

    @Autowired
    private ILoanFacade loanFacade;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LoanApplicationResponse> applyForLoan(@RequestBody @Valid LoanApplicationRequestDto request) {

        Loan loan = loanFacade.applyForLoan(toLoanApplication(request));

        return new ResponseEntity<>(toLoanResponse(loan), HttpStatus.OK);
    }

    private LoanApplication toLoanApplication(LoanApplicationRequestDto request) {
        LoanApplication loanApplication = new LoanApplication();
        BeanUtils.copyProperties(request, loanApplication);
        loanApplication.setDateCreated(LocalDateTime.now());
        return loanApplication;
    }

    private LoanApplicationResponse toLoanResponse(Loan loan) {
        LoanApplicationResponse response = new LoanApplicationResponse();
        BeanUtils.copyProperties(loan, response);
        response.setFirstName(loan.getClient().getFirstName());
        response.setSurname(loan.getClient().getSurname());
        response.setClientId(loan.getClient().getClientId());

        return response;
    }
}
