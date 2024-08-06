package com.TransferApp.MoneyTransfer.controllerTest;

import com.TransferApp.MoneyTransfer.controller.TransactionController;
import com.TransferApp.MoneyTransfer.dto.TransferRequestDTO;
import com.TransferApp.MoneyTransfer.enums.TransactionType;
import com.TransferApp.MoneyTransfer.model.Account;
import com.TransferApp.MoneyTransfer.model.Transaction;
import com.TransferApp.MoneyTransfer.service.ITransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TransactionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ITransaction transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    public void testTransferMoney() throws Exception {
        doNothing().when(transactionService).transferMoney(any(TransferRequestDTO.class));

        mockMvc.perform(post("/api/transactions/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sourceAccountId\":1,\"accountNumber\":\"1234567\",\"amount\":100.0,\"transactionType\":\"DEPOSIT\"}"))
                .andExpect(status().isOk());

        verify(transactionService, times(1)).transferMoney(any(TransferRequestDTO.class));
    }

    @Test
    public void testTransferMoney_ValidationError() throws Exception {
        // Test with invalid amount (e.g., negative value)
        mockMvc.perform(post("/api/transactions/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sourceAccountId\":1,\"destinationAccountId\":2,\"amount\":-100.0,\"transactionType\":\"DEPOSIT\"}")) // Invalid amount
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testTransferMoney_InvalidInput() throws Exception {
        // Test with invalid JSON (e.g., missing required fields)
        mockMvc.perform(post("/api/transactions/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sourceAccountId\":1,\"amount\":100.0,\"transactionType\":\"DEPOSIT\"}")) // Missing destinationAccountId
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetTransactionById() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setId(1L); // Ensure the id is set
        Account fromAccount = new Account();
        fromAccount.setId(1L);
        Account toAccount = new Account();
        toAccount.setId(2L);
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAmount(100.0);
        transaction.setTransactionType(TransactionType.DEPOSIT);

        when(transactionService.getTransactionById(anyLong())).thenReturn(Optional.of(transaction));

        mockMvc.perform(get("/api/transactions/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L)); // Check that id exists and has the correct value

        verify(transactionService, times(1)).getTransactionById(anyLong());
    }
    @Test
    public void testGetTransactionById_NotFound() throws Exception {
        Long id = 1L;

        when(transactionService.getTransactionById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/transactions/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(transactionService, times(1)).getTransactionById(id);
    }


    @Test
    void testGetTransactionsByAccountId_Success() throws Exception {
        Long accountId = 1L;
        List<Transaction> transactions = List.of(
              Transaction.builder().id(1L).amount(100.0).fromAccount(new Account()).toAccount(new Account()).transactionType(TransactionType.DEPOSIT).build(),
              Transaction.builder().id(2L).amount(50.0).fromAccount(new Account()).toAccount(new Account()).transactionType(TransactionType.WITHDRAWAL).build()
        );
        Page<Transaction> page = new PageImpl<>(transactions, PageRequest.of(0, 10), transactions.size());

        when(transactionService.getTransactionsByAccountId(accountId)).thenReturn(page);

        mockMvc.perform(get("/api/transactions/account/{accountId}", accountId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].amount").value(100.00));
    }

    @Test
    void testGetTransactionsByAccountId_Empty() throws Exception {
        Long accountId = 999L; // Use an account ID that you expect to return an empty page

        // Mock the service to return an empty page
        Page<Transaction> emptyPage = Page.empty(PageRequest.of(0, 2));
        when(transactionService.getTransactionsByAccountId(accountId)).thenReturn(emptyPage);

        mockMvc.perform(get("/api/transactions/account/{accountId}", accountId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isEmpty());
    }



    @Test
    public void testDeleteTransaction() throws Exception {
        doNothing().when(transactionService).deleteTransaction(anyLong());

        mockMvc.perform(delete("/api/transactions/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(transactionService, times(1)).deleteTransaction(anyLong());
    }

}
