package victor.bonneau.kata.bankAccount.mappeur;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import victor.bonneau.kata.bankAccount.dto.AccountDto;
import victor.bonneau.kata.bankAccount.model.Account;

public class AccountMappeurTest {

    private AccountMappeur mappeur = new AccountMappeur();
    private Account account;
    private AccountDto accountDto;
    
    @BeforeEach
    void setUp() {
    	account = new Account();
        account.setId(1);
        account.setBalance(100);
        account.setUserId(1);

        accountDto = new AccountDto();
        accountDto.setId(1);
        accountDto.setBalance(100);
        accountDto.setUserId(1);
    }
    
    /*------------------ accountToDto ----------------------------------*/
    @Test
    void accountToDtoWihtNullShouldReturnEmptyList() {
        AccountDto accountDtoMapper = mappeur.accountToDto(null);
        assertThat(accountDtoMapper).isNull();
    }
    
    @Test
    void accountToDtoWihthAccountShouldRetunDtoMappedFieldByField() {
        AccountDto accountDtoMapper = mappeur.accountToDto(account);
        assertThat(accountDtoMapper).isEqualTo(accountDto);
    }
    
}