package victor.bonneau.kata.bankAccount.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import victor.bonneau.kata.bankAccount.exception.ObjectNotFoundException;
import victor.bonneau.kata.bankAccount.model.Account;

@SpringBootTest
@Transactional
public class AccountRepositoryTest {
	
	private Account account;
	private Account account2;
    private AccountRepository accountRepository;
    @PersistenceContext
    private EntityManager em;
    
    @BeforeEach
    void setUp() {
    	account = new Account();
        account.setId(1);
        account.setBalance(100);
        account.setUserId(1);
        
        account2 = new Account();
        account2.setId(2);
        account2.setBalance(500);
        account2.setUserId(2);
    }

    /*-------------------- getById --------------------*/
    
    @Test
    void getByIdReturnAccount() throws ObjectNotFoundException {
        Account fetchedAccount = accountRepository.getAccount(1);
        
        assertThat(fetchedAccount).isEqualTo(account);
    }
    
    @Test
    void getByIdShouldThrowObjectNotFound() {
        Exception exception = assertThrows(ObjectNotFoundException.class, ()->accountRepository.getAccount(13));
        
        String excpectMessage = "Account not found.";
        assertThat(exception.getMessage()).contains(excpectMessage);
    }
    
    /*-------------------- saveOrUpdate --------------------*/
    
    @Test
    void saveOrUpdateShouldSaveNewAccount() throws ObjectNotFoundException {
    	Account newAccount = new Account();
    	newAccount.setId(0);
    	newAccount.setBalance(1000);
    	newAccount.setUserId(2);
        
        Account excepteAccount = new Account();
        excepteAccount.setId(3);
        newAccount.setBalance(1000);
    	newAccount.setUserId(2);
        
        Account fetchAccount = accountRepository.saveOrUpdate(newAccount);
        
        assertThat(fetchAccount).isEqualTo(excepteAccount);
        assertThat(accountRepository.getAccount(3)).isEqualTo(excepteAccount);
    }
    
    @Test
    void saveOrUpdateShouldUpdateOldAccount() throws ObjectNotFoundException {
    	account2 = new Account();
        account2.setId(2);
        account2.setBalance(600);
        account2.setUserId(2);
        
        Account fetchAccount = accountRepository.saveOrUpdate(account2);
        
        assertThat(fetchAccount).isEqualTo(account2);
        assertThat(accountRepository.getAccount(2)).isEqualTo(account2);
    }
}
