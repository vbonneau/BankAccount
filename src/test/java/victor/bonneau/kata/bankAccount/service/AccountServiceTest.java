package victor.bonneau.kata.bankAccount.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import victor.bonneau.kata.bankAccount.model.Account;
import victor.bonneau.kata.bankAccount.repository.AccountRepository;

public class AccountServiceTest {

    private Account account;
    private AccountRepository repository;
    AccountService service;
    @BeforeEach
    void setUp() {
        account = new Account();
        account.setId(0);
        account.setBalance(0);
        account.setUserId(1);


        repository = mock(AccountRepository.class);
        service = new AccountService(repository);
    }

    /*--------------------create--------------------*/
    @Test
    void createRetunAccount() {
        
        when(repository.saveOrUpdate(account)).thenReturn(account);
        
        Account result = service.create(1);
        assertThat(result).isEqualTo(account);
        verify(repository, times(1)).saveOrUpdate(account);
        verifyNoMoreInteractions(repository);
    }

}
