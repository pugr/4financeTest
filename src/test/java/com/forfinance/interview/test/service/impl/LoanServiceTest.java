package com.forfinance.interview.test.service.impl;

import static com.forfinance.interview.test.util.LoanTestUtils.AMOUNT;
import static com.forfinance.interview.test.util.LoanTestUtils.CLIENT_ID;
import static com.forfinance.interview.test.util.LoanTestUtils.FIRST_NAME;
import static com.forfinance.interview.test.util.LoanTestUtils.SURNAME;
import static com.forfinance.interview.test.util.LoanTestUtils.TERM;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.forfinance.interview.test.domain.Client;
import com.forfinance.interview.test.domain.Loan;
import com.forfinance.interview.test.dto.LoanApplication;
import com.forfinance.interview.test.repository.ClientRepository;
import com.forfinance.interview.test.repository.LoanRepository;
import com.forfinance.interview.test.util.LoanTestUtils;

/**
 * Test suite for {@link LoanService}.
 * 
 * @author Jan Koren
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class LoanServiceTest {

    @InjectMocks
    private LoanService loanService;
    
    @Mock
    private LoanRepository loanRepository;
    
    @Mock
    private ClientRepository clientRepository;
    
    @Mock
    private HttpServletRequest request;
    
    @Before
    public void setup() {
        when(request.getRemoteAddr()).thenReturn(LoanTestUtils.IP_ADDRESSS);
    }
    
    @Test
    public void testCreateLoanNoClientExists() {
        LoanApplication loanApplication = LoanTestUtils.createLoanApplication(CLIENT_ID, AMOUNT, TERM, LocalDateTime.now());
        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgumentAt(0, Loan.class));
        when(clientRepository.findOne(Mockito.eq(CLIENT_ID))).thenReturn(null);
        Loan loan = loanService.createLoan(loanApplication);
        
        assertNotNull(loan);
        assertNotNull(loan.getClient());
        assertEquals(loan.getClient().getClientId().longValue(), CLIENT_ID);
        Mockito.verify(loanRepository).save(any(Loan.class));
        Mockito.verifyNoMoreInteractions(loanRepository);
    }
    
    @Test
    public void testCreateLoanClientAlreadyExists() {
        LoanApplication loanApplication = LoanTestUtils.createLoanApplication(CLIENT_ID, AMOUNT, TERM, LocalDateTime.now());
        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgumentAt(0, Loan.class));
        Client client = createClient();
        when(clientRepository.findOne(Mockito.eq(CLIENT_ID))).thenReturn(client);
        Loan loan = loanService.createLoan(loanApplication);
        
        assertNotNull(loan);
        assertNotNull(loan.getClient());
        assertEquals(loan.getClient(), client);
        Mockito.verify(loanRepository).save(any(Loan.class));
        Mockito.verifyNoMoreInteractions(loanRepository);
    }

    private Client createClient() {
        Client client = new Client();
        client.setClientId(CLIENT_ID);
        client.setFirstName(FIRST_NAME);
        client.setSurname(SURNAME);
        return client;
    }
}
