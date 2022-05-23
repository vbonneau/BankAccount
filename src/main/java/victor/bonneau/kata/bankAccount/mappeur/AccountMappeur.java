package victor.bonneau.kata.bankAccount.mappeur;

import org.springframework.stereotype.Component;

import victor.bonneau.kata.bankAccount.dto.AccountDto;
import victor.bonneau.kata.bankAccount.model.Account;

@Component
public class AccountMappeur {

	public AccountDto accountToDto(Account account) {
		AccountDto dto = new AccountDto();
		dto.setBalance(account.getBalance());
		dto.setId(account.getId());
		return dto;
	}
}
