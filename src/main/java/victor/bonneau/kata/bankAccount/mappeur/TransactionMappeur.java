package victor.bonneau.kata.bankAccount.mappeur;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import victor.bonneau.kata.bankAccount.dto.TransactionDto;
import victor.bonneau.kata.bankAccount.model.Transaction;


@Component
public class TransactionMappeur {

    public TransactionDto transactionToDto(Transaction transaction){
        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setType(transaction.getType());
        dto.setAccountId(transaction.getAccountId());
        dto.setAmount(transaction.getAmount());
        dto.setDate(transaction.getDate());
        dto.setBalenceBefor(transaction.getBalenceBefor());
        dto.setBalenceAfter(transaction.getBalenceAfter());
        return dto;
    }
    
    public List<TransactionDto> transactionsToDtos(List<Transaction> transactions){
        if(transactions == null) return new ArrayList<TransactionDto>();
        return transactions.stream().filter(Objects::nonNull).map(transaction -> transactionToDto(transaction))
                .collect(Collectors.toList());
        
    }

    public Transaction dtoToTransaction(TransactionDto dto) {
    	Transaction transaction = new Transaction();
    	transaction.setId(dto.getId());
    	transaction.setType(dto.getType());
    	transaction.setAccountId(dto.getAccountId());
    	transaction.setDate(dto.getDate());
    	transaction.setBalenceBefor(dto.getBalenceBefor());
    	transaction.setBalenceAfter(dto.getBalenceAfter());
        return transaction;
    }
    
    public List<Transaction> dtosToTransactions(List<TransactionDto> dtos){
        if(dtos == null) return new ArrayList<Transaction>();
        return dtos.stream().filter(Objects::nonNull).map(dto -> dtoToTransaction(dto))
                .collect(Collectors.toList());
        
    }
}