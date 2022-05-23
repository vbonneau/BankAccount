package victor.bonneau.kata.bankAccount.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import victor.bonneau.kata.bankAccount.dto.AccountDto;
import victor.bonneau.kata.bankAccount.model.Account;
import victor.bonneau.kata.bankAccount.service.AccountService;
import victor.bonneau.kata.bankAccount.mappeur.AccountMappeur;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


public class AccountControllerTest {

    private Account account;
    private AccountDto accountDto;
    private MockMvc mockMvc;
    private AccountMappeur mappeur;
    private AccountService service;
    private ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule());

    

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

        service = mock(AccountService.class);
        mappeur = mock(AccountMappeur.class);
        AccountController controller = new AccountController(service, mappeur);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new CustomGlobalExceptionHandler())
                .build();
    }
    /*--------------------create--------------------*/
    @Test
    void createShouldReturnOkAndTheAccount() throws Exception {
        accountDto.setId(0);
        account.setId(0);
        when(service.create(1)).thenReturn(account);
        when(mappeur.accountToDto(account)).thenReturn(accountDto);

        String mockResult = mockMvc
                .perform(post("/account/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(service, times(1)).create(1);
        verifyNoMoreInteractions(service);

        AccountDto result = objectMapper.readValue(mockResult, AccountDto.class);
        assertThat(result).isEqualTo(accountDto);
    }

    @Test
    void createShouldReturn400WhenUserId0() throws Exception {
        accountDto.setUserId(0);

        mockMvc.perform(post("/account/0")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(accountDto)))
        .andExpect(status().isBadRequest());
    }

}
