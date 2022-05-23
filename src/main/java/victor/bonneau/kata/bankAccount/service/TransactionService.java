package victor.bonneau.kata.bankAccount.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import victor.bonneau.kata.bankAccount.exception.ObjectNotFoundException;
import victor.bonneau.kata.bankAccount.model.Account;
import victor.bonneau.kata.bankAccount.model.Transaction;
import victor.bonneau.kata.bankAccount.repository.AccountRepository;
import victor.bonneau.kata.bankAccount.repository.TransactionRepository;

@Service
public class TransactionService {
	
	private final TransactionRepository transactionRipository;
	private final AccountRepository accountRepository;
   

    public TransactionService(TransactionRepository transactionRipository, AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
		this.transactionRipository = transactionRipository;
    }

    @Transactional
	public List<Transaction> getAllForAccount(int accountId) throws ObjectNotFoundException {
		return transactionRipository.getAllForAccount(accountId);
	}

    @Transactional
	public Transaction create(Transaction transaction) throws ObjectNotFoundException {
		Account account = accountRepository.getAccount(transaction.getAccountId());
		double balance = account.getBalance();
		transaction.setBalenceBefor(balance);
		transaction.setDate(LocalDateTime.now());
		switch(transaction.getType()) {
			case deposit:
				balance += transaction.getAmount();
				break;
			case withdrawal:
				balance -= transaction.getAmount();
				break;
		}
		account.setBalance(balance);
		transaction.setBalenceAfter(balance);
		accountRepository.saveOrUpdate(account);
		transaction =  transactionRipository.saveOrUpdate(transaction);
		return transaction;
	}

}
