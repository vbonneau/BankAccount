package victor.bonneau.kata.bankAccount.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import victor.bonneau.kata.bankAccount.dto.TransactionDto;
import victor.bonneau.kata.bankAccount.exception.ObjectNotFoundException;
import victor.bonneau.kata.bankAccount.mappeur.TransactionMappeur;
import victor.bonneau.kata.bankAccount.model.enums.TransactionType;
import victor.bonneau.kata.bankAccount.service.TransactionService;

@RestController
@RequestMapping("transaction")
public class TransactionController {

    private final TransactionMappeur mappeur;
	private final TransactionService service;

    public TransactionController(TransactionService service, TransactionMappeur mappeur) {
    	this.mappeur = mappeur;
    	this.service = service;
    }
    
    @GetMapping("/{accountId}")
    public ResponseEntity<List<TransactionDto>> getAllForAccount(@PathVariable int accountId) throws ObjectNotFoundException {
    	List<TransactionDto> dtos = mappeur.transactionsToDtos(service.getAllForAccount(accountId));
    	return ResponseEntity.ok(dtos);
    }
    
    @PostMapping("/{accountId}/deposit/{amount}")
    public ResponseEntity<TransactionDto> deposit(@PathVariable int accountId, @PathVariable double amount) throws ObjectNotFoundException {
		TransactionDto dto = new TransactionDto();
    	dto.setAccountId(accountId);
    	dto.setAmount(amount);
    	dto.setType(TransactionType.deposit);
    	dto = mappeur.transactionToDto(service.create(mappeur.dtoToTransaction(dto)));
    	return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    
    @PostMapping("/{accountId}/withdrawal/{amount}")
    public ResponseEntity<TransactionDto> withdrawal(@PathVariable int accountId, @PathVariable double amount) throws ObjectNotFoundException {
		TransactionDto dto = new TransactionDto();
    	dto.setAccountId(accountId);
    	dto.setAmount(amount);
    	dto.setType(TransactionType.withdrawal);
    	dto = mappeur.transactionToDto(service.create(mappeur.dtoToTransaction(dto)));
    	return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
