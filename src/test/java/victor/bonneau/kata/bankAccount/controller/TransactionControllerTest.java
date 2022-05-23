package victor.bonneau.kata.bankAccount.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import victor.bonneau.kata.bankAccount.dto.TransactionDto;
import victor.bonneau.kata.bankAccount.service.TransactionService;
import victor.bonneau.kata.bankAccount.mappeur.TransactionMappeur;
import victor.bonneau.kata.bankAccount.model.Transaction;
import victor.bonneau.kata.bankAccount.model.enums.TransactionType;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TransactionControllerTest {

    private Transaction transaction;
    private TransactionDto transactionDto;
    private MockMvc mockMvc;
    private TransactionMappeur mappeur;
    private TransactionService service;
    private ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule());

    

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

        service = mock(TransactionService.class);
        mappeur = mock(TransactionMappeur.class);
        TransactionController controller = new TransactionController(service, mappeur);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new CustomGlobalExceptionHandler())
                .build();
    }


    /*--------------------deposit--------------------*/
    @Test
    void depositShouldReturnOkAndTheTransaction() throws Exception {
        transaction.setType(TransactionType.deposit);
        transactionDto.setType(TransactionType.deposit);
        when(mappeur.dtoToTransaction(transactionDto)).thenReturn(transaction);
        when(service.create(transaction)).thenReturn(transaction);
        when(mappeur.transactionToDto(transaction)).thenReturn(transactionDto);

        String mockResult = mockMvc
                .perform(post("/transaction/1/deposit/20")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(transactionDto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(service, times(1)).create(transaction);
        verifyNoMoreInteractions(service);

        TransactionDto result = objectMapper.readValue(mockResult, TransactionDto.class);
        assertThat(result).isEqualTo(transactionDto);
    }

     /*--------------------withdrawal--------------------*/
     @Test
     void withdrawalShouldReturnOkAndTheTransaction() throws Exception {
         transaction.setType(TransactionType.withdrawal);
         transactionDto.setType(TransactionType.withdrawal);
         when(mappeur.dtoToTransaction(transactionDto)).thenReturn(transaction);
         when(service.create(transaction)).thenReturn(transaction);
         when(mappeur.transactionToDto(transaction)).thenReturn(transactionDto);
 
         String mockResult = mockMvc
                 .perform(post("/transaction/1/withdrawal/20")
                         .contentType(MediaType.APPLICATION_JSON_VALUE)
                         .content(objectMapper.writeValueAsString(transactionDto)))
                 .andExpect(status().isCreated())
                 .andReturn()
                 .getResponse()
                 .getContentAsString();
 
         verify(service, times(1)).create(transaction);
         verifyNoMoreInteractions(service);
 
         TransactionDto result = objectMapper.readValue(mockResult, TransactionDto.class);
         assertThat(result).isEqualTo(transactionDto);
     }

     /*--------------------getForAccount--------------------*/
     @Test
     void getForAccountShouldReturnOkAndTheTransaction() throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        List<TransactionDto> transactionDtos = new ArrayList<>();
        transactionDtos.add(transactionDto);
        when(service.getAllForAccount(1)).thenReturn(transactions);
        when(mappeur.transactionsToDtos(transactions)).thenReturn(transactionDtos);
 
        String mockResult = mockMvc
                .perform(get("/transaction/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(transactionDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(service, times(1)).getAllForAccount(1);
        verifyNoMoreInteractions(service);

        List<TransactionDto> result = Arrays.asList(objectMapper.readValue(mockResult, TransactionDto[].class));
        assertThat(result).isEqualTo(transactionDtos);
     }
}
