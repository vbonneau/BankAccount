package victor.bonneau.kata.bankAccount.service;

import org.springframework.transaction.annotation.Transactional;

import victor.bonneau.kata.bankAccount.model.Account;
import victor.bonneau.kata.bankAccount.repository.AccountRepository;

public class AccountService {
	
	private final AccountRepository accountRepository;
   

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
	public Account create(int userId) {
		Account account = new Account();
		account.setUserId(userId);
		return accountRepository.saveOrUpdate(account);
	}

}
