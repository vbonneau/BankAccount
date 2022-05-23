package victor.bonneau.kata.bankAccount.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import victor.bonneau.kata.bankAccount.exception.ObjectNotFoundException;
import victor.bonneau.kata.bankAccount.model.Transaction;
import victor.bonneau.kata.bankAccount.model.enums.TransactionType;


@SpringBootTest
@Transactional
public class TransactionRepositoryTest {
    
    private Transaction transaction;
    private TransactionRepository transactionRepository;
    @PersistenceContext
    private EntityManager em;
    
    @BeforeEach
    void setUp() {
    	transaction = new Transaction();
        transaction.setId(1);
        transaction.setType(TransactionType.deposit);
        transaction.setAccountId(1);
        transaction.setAmount(20);
        transaction.setBalenceAfter(80);
        transaction.setBalenceBefor(100);
        transaction.setDate(LocalDateTime.of(2022, 05, 12, 0, 0));
        
        transactionRepository = new TransactionRepository(em);
    }
    
    /*-------------------- getById --------------------*/
    
    @Test
    void getByIdReturnTransaction() throws ObjectNotFoundException {
    	List<Transaction> fetchedTransactions = transactionRepository.getAllForAccount(1);
        
        assertThat(fetchedTransactions).hasSize(1);
        assertThat(fetchedTransactions.get(0)).isEqualTo(transaction);
    }
    
    @Test
    void getByIdShouldThrowObjectNotFound() {
        Exception exception = assertThrows(ObjectNotFoundException.class, ()->transactionRepository.getAllForAccount(13));
        
        String excpectMessage = "Transaction not found.";
        assertThat(exception.getMessage()).contains(excpectMessage);
    }
    
    /*-------------------- saveOrUpdate --------------------*/
    
    @Test
    void saveOrUpdateShouldSaveNewTransaction() throws ObjectNotFoundException {
        Transaction newTransaction = new Transaction();
        newTransaction.setId(0);
        newTransaction.setType(TransactionType.withdrawal);
        newTransaction.setAccountId(1);
        newTransaction.setAmount(20);
        newTransaction.setBalenceAfter(100);
        newTransaction.setBalenceBefor(80);
        newTransaction.setDate(LocalDateTime.of(2022, 05, 12, 0, 0));
        
        Transaction excepteTransaction = new Transaction();
        excepteTransaction.setId(2);
        excepteTransaction.setType(TransactionType.withdrawal);
        excepteTransaction.setAccountId(1);
        excepteTransaction.setAmount(20);
        excepteTransaction.setBalenceAfter(100);
        excepteTransaction.setBalenceBefor(80);
        excepteTransaction.setDate(LocalDateTime.of(2022, 05, 12, 0, 0));
        
        Transaction fetchTransaction = transactionRepository.saveOrUpdate(newTransaction);
        
        assertThat(fetchTransaction).isEqualTo(excepteTransaction);
        assertThat(transactionRepository.getAllForAccount(1)).hasSize(2);
    }

}
