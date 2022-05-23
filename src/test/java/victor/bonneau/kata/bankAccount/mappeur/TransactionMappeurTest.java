package victor.bonneau.kata.bankAccount.mappeur;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import victor.bonneau.kata.bankAccount.dto.TransactionDto;
import victor.bonneau.kata.bankAccount.model.Transaction;
import victor.bonneau.kata.bankAccount.model.enums.TransactionType;

public class TransactionMappeurTest {

    private TransactionMappeur mappeur = new TransactionMappeur();
    private Transaction transaction;
    private TransactionDto transactionDto;
    
    @BeforeEach
    void setUp() {
    	transaction = new Transaction();
        transaction.setId(0);
        transaction.setAccountId(1);
        transaction.setAmount(20);
        transaction.setDate(null);
        transaction.setType(TransactionType.deposit);
        transaction.setBalenceAfter(0);
        transaction.setBalenceBefor(0);

        transactionDto = new TransactionDto();
        transactionDto.setId(0);
        transactionDto.setAccountId(1);
        transactionDto.setAmount(20);
        transactionDto.setDate(null);
        transactionDto.setType(TransactionType.deposit);
        transactionDto.setBalenceAfter(0);
        transactionDto.setBalenceBefor(0);
    }
    
    /*------------------ dtoToTransaction ----------------------------------*/
    @Test
    void dtoToTransactionWithNullShouldReturnNull() {
        
        Transaction transactionMapper = mappeur.dtoToTransaction(null);
        assertThat(transactionMapper).isNull();
    }
    
    @Test
    void dtoToTransactionWithDtoShouldRetunTransactionMappedFieldByField() {
        Transaction transactionMapper = mappeur.dtoToTransaction(transactionDto);
        assertThat(transactionMapper).isEqualTo(transaction);
    }
    
    /*------------------ dtoToTransactions ----------------------------------*/
    @Test
    void dtoToTransactionsWithNullListShouldReturnEmptyList() {
        
        List<Transaction> transactionsMapper = mappeur.dtosToTransactions(null);
        assertThat(transactionsMapper).isEmpty();
    }
    
    @Test
    void dtoToTransactionsWithEmptyListListShouldReturnEmptyList() {
        List<TransactionDto> transactionDtos = new ArrayList<TransactionDto>();
        
        List<Transaction> transactionsMapper = mappeur.dtosToTransactions(transactionDtos);
        assertThat(transactionsMapper).isEmpty();
    }
    
    @Test
    void dtoToTransactionsWithNoEmptyListListShouldReturnNoEmptyListWithSameSize() {
        List<TransactionDto> transactionDtos = Collections.singletonList(transactionDto);
        
        List<Transaction> transactionsMapper = mappeur.dtosToTransactions(transactionDtos);
        assertThat(transactionsMapper).hasSize(1);
        Transaction transactionMapper = transactionsMapper.get(0);
        assertThat(transactionMapper).isEqualTo(transaction);
    }
    
    /*------------------ transactionToDto ----------------------------------*/
    @Test
    void transactionToDtoWihtNullShouldReturnEmptyList() {
        TransactionDto transactionDtoMapper = mappeur.transactionToDto(null);
        assertThat(transactionDtoMapper).isNull();
    }
    
    @Test
    void transactionToDtoWihthTransactionShouldRetunDtoMappedFieldByField() {
        TransactionDto transactionDtoMapper = mappeur.transactionToDto(transaction);
        assertThat(transactionDtoMapper).isEqualTo(transactionDto);
    }
    
    /*------------------ transactionToDtos ----------------------------------*/
    @Test
    void transactionToDtosWithNullListShouldReturnEmptyList() {
        List<TransactionDto> transactionDtosMapper = mappeur.transactionsToDtos(null);
        assertThat(transactionDtosMapper).isEmpty();
    }
    
    @Test
    void transactionToDtosWithEmptyListListShouldReturnEmptyList() {
        List<Transaction> transactions = new ArrayList<Transaction>();
        
        List<TransactionDto> transactionDtosMapper = mappeur.transactionsToDtos(transactions);
        assertThat(transactionDtosMapper).isEmpty();
    }
    
    @Test
    void transactionToDtosWithNoEmptyListListShouldReturnNoEmptyListWithSameSize() {
        List<Transaction> transactions = Collections.singletonList(transaction);
        
        List<TransactionDto> transactionDtosMapper = mappeur.transactionsToDtos(transactions);
        assertThat(transactionDtosMapper).hasSize(1);
        TransactionDto transactionDtoMapper = transactionDtosMapper.get(0);
        assertThat(transactionDtoMapper).isEqualTo(transactionDto);
    }
}
