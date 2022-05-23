package victor.bonneau.kata.bankAccount.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import victor.bonneau.kata.bankAccount.exception.ObjectNotFoundException;
import victor.bonneau.kata.bankAccount.model.Account;
import victor.bonneau.kata.bankAccount.model.Transaction;
import victor.bonneau.kata.bankAccount.model.enums.TransactionType;
import victor.bonneau.kata.bankAccount.repository.AccountRepository;
import victor.bonneau.kata.bankAccount.repository.TransactionRepository;

public class TransactionServiceTest {
	
	private Transaction transaction;
	private Account account;
	private LocalDateTime date;
    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;
    private TransactionService service;
    
    @BeforeEach
    void setUp() {
    	date = LocalDateTime.now();
    	
        transaction = new Transaction();
        transaction.setId(0);
        transaction.setType(null);
        transaction.setAccountId(1);
        transaction.setBalenceAfter(0);
        transaction.setBalenceBefor(100);
        transaction.setDate(date);
        
        account = new Account();
        account.setId(1);
        account.setBalance(100);
        
        transactionRepository = mock(TransactionRepository.class);
        accountRepository = mock(AccountRepository.class);
        
        service = new TransactionService(transactionRepository, accountRepository);
    }
    
    /*--------------------get all for account--------------------*/
    @Test
    void getAllShouldRetunTransactionList() throws ObjectNotFoundException {
        
        when(transactionRepository.getAllForAccount(1)).thenReturn(Collections.singletonList(transaction));
        
        List<Transaction> results = service.getAllForAccount(1);
        assertThat(results).hasSize(1);
        assertThat(results.get(0)).isEqualTo(transaction);
        verify(transactionRepository, times(1)).getAllForAccount(1);
        verifyNoMoreInteractions(transactionRepository);
    }
    
    /*--------------------create--------------------*/
    @Test
    void createShouldDeposit() throws ObjectNotFoundException {
    	
    	transaction.setType(TransactionType.deposit);
    	transaction.setBalenceAfter(120);
    	
    	when(accountRepository.getAccount(1)).thenReturn(account);
        when(transactionRepository.saveOrUpdate(transaction)).thenReturn(transaction);
        try(MockedStatic<LocalDateTime> mock = Mockito.mockStatic(LocalDateTime.class)) {
    	    mock.when(LocalDateTime::now).thenReturn(date);
    	}
    	
    	Transaction result = service.create(transaction);
    	
    	assertThat(result).isEqualTo(transaction);
    }
    
    @Test
    void createShouldWithdrawal() throws ObjectNotFoundException {
    	
    	transaction.setType(TransactionType.withdrawal);
    	transaction.setBalenceAfter(80);
    	
    	when(accountRepository.getAccount(1)).thenReturn(account);
        when(transactionRepository.saveOrUpdate(transaction)).thenReturn(transaction);
        try(MockedStatic<LocalDateTime> mock = Mockito.mockStatic(LocalDateTime.class)) {
    	    mock.when(LocalDateTime::now).thenReturn(date);
    	}
    	
    	Transaction result = service.create(transaction);
    	
    	assertThat(result).isEqualTo(transaction);
    }

}
