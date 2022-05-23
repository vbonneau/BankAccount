package victor.bonneau.kata.bankAccount.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import victor.bonneau.kata.bankAccount.dto.AccountDto;
import victor.bonneau.kata.bankAccount.mappeur.AccountMappeur;
import victor.bonneau.kata.bankAccount.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

	private final AccountService service;
	private final AccountMappeur mappeur;

    public AccountController(AccountService service, AccountMappeur mappeur ) {
    	this.mappeur = mappeur;
    	this.service = service;
    }
    
    @PostMapping("/{userId}")
    public ResponseEntity<AccountDto> create(@PathVariable int userId){
        if(userId == 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        AccountDto dto = mappeur.accountToDto(service.create(userId));
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
