package com.TransferApp.MoneyTransfer.controllerTest;

import com.TransferApp.MoneyTransfer.controller.AccountController;
import com.TransferApp.MoneyTransfer.dto.UpdateAccountDto;
import com.TransferApp.MoneyTransfer.dto.accountDTO;
import com.TransferApp.MoneyTransfer.enums.Currency;
import com.TransferApp.MoneyTransfer.model.Account;
import com.TransferApp.MoneyTransfer.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AccountControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    public void testGetAllAccounts() throws Exception {
        Account account = new Account();
        account.setId(1L);
        List<Account> accounts = Arrays.asList(account);

        when(accountService.getAllAccounts()).thenReturn(accounts);

        mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)));

        verify(accountService, times(1)).getAllAccounts();
    }


    @Test
    public void testGetAccountById() throws Exception {
        Account account = new Account();
        account.setId(1L);

        when(accountService.getAccountById(1L)).thenReturn(Optional.of(account));

        mockMvc.perform(get("/api/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

        verify(accountService, times(1)).getAccountById(1L);
    }


    @Test
    public void testGetAccountsByCustomerId() throws Exception {
        Account account = new Account();
        account.setId(1L);
        List<Account> accounts = Arrays.asList(account);

        when(accountService.getAccountsByCustomerId(1L)).thenReturn(accounts);

        mockMvc.perform(get("/api/accounts/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)));

        verify(accountService, times(1)).getAccountsByCustomerId(1L);
    }

    @Test
    public void testUpdateAccount() throws Exception {
        UpdateAccountDto updateAccountDto = new UpdateAccountDto();
        Account account = new Account();
        account.setId(1L);

        when(accountService.updateAccount(eq(1L), any(UpdateAccountDto.class))).thenReturn(account);

        mockMvc.perform(put("/api/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountName\": \"Updated Account\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

        verify(accountService, times(1)).updateAccount(eq(1L), any(UpdateAccountDto.class));
    }

    @Test
    public void testDeleteAccount() throws Exception {
        doNothing().when(accountService).deleteAccount(1L);

        mockMvc.perform(delete("/api/accounts/1"))
                .andExpect(status().isNoContent());

        verify(accountService, times(1)).deleteAccount(1L);
    }


    @Test
    public void testChangeCurrency() throws Exception {
        accountDTO accountDto = accountDTO.builder()
                .id(1L)
                .currency(Currency.USD)
                .build();

        when(accountService.changeCurrency(eq(1L), eq(Currency.USD))).thenReturn(accountDto);

        mockMvc.perform(post("/api/accounts/currency/1/USD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.currency", is("USD")));

        verify(accountService, times(1)).changeCurrency(eq(1L), eq(Currency.USD));
    }


}