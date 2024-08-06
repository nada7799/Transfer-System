package com.TransferApp.MoneyTransfer.serviceTest;

import com.TransferApp.MoneyTransfer.dto.CreateAccountDto;
import com.TransferApp.MoneyTransfer.dto.UpdateAccountDto;
import com.TransferApp.MoneyTransfer.dto.accountDTO;
import com.TransferApp.MoneyTransfer.enums.AccountType;
import com.TransferApp.MoneyTransfer.enums.Currency;
import com.TransferApp.MoneyTransfer.exception.CustomerAlreadyExistsException;
import com.TransferApp.MoneyTransfer.exception.CustomerNotFoundException;
import com.TransferApp.MoneyTransfer.model.Account;
import com.TransferApp.MoneyTransfer.model.Customer;
import com.TransferApp.MoneyTransfer.reporsitory.AccountRepository;
import com.TransferApp.MoneyTransfer.reporsitory.CustomerRepository;
import com.TransferApp.MoneyTransfer.service.AccountService;
import com.TransferApp.MoneyTransfer.service.CurrencyConverter;
import com.TransferApp.MoneyTransfer.service.InputSanitizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CurrencyConverter currencyConverter;
    @Mock
    private InputSanitizer  inputSanitizer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAccount() throws CustomerNotFoundException {
        // Arrange
        long customerId = 1L;
        CreateAccountDto createAccountDto = CreateAccountDto.builder()
                .accountType(AccountType.SAVING)
                .balance(1000.0)
                .currency(Currency.USD)
                .accountDescription("Savings Account")
                .build();
        Customer customer = new Customer();
        customer.setId(customerId);

        Account account = Account.builder()
                .accountNumber(String.valueOf(new SecureRandom().nextInt(100000000)))
                .accountName(customer.getFirstName() + " " + customer.getLastName())
                .accountType(AccountType.SAVING)
                .balance(1000.0)
                .currency(Currency.USD)
                .customer(customer)
                .description("Savings Account")
                .build();
        when(inputSanitizer.sanitize(anyString())).thenCallRealMethod();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        // Act
        Account createdAccount;
        try {
            createdAccount = accountService.createAccount(createAccountDto, customerId);
            // Assert
            assertNotNull(createdAccount);
            assertEquals(AccountType.SAVING, createdAccount.getAccountType());
            assertEquals("Savings Account", createdAccount.getDescription());
        } catch (CustomerAlreadyExistsException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    @Test
    public void testUpdateAccount() {
        // Arrange
        Long accountId = 1L;
        UpdateAccountDto updateAccountDto = new UpdateAccountDto();
        updateAccountDto.setAccountType(AccountType.SAVING);
        updateAccountDto.setAccountName("Updated Account");
        updateAccountDto.setAccountDescription("Updated description");
        updateAccountDto.setActive(true);

        Account existingAccount = Account.builder()
                .accountType(AccountType.SAVING)
                .accountName("Old Account")
                .description("Old description")
                .build();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(existingAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(existingAccount);

        // Act
        Account updatedAccount = accountService.updateAccount(accountId, updateAccountDto);

        // Assert
        assertNotNull(updatedAccount);
        assertEquals(AccountType.SAVING, updatedAccount.getAccountType());
        assertEquals("Updated Account", updatedAccount.getAccountName());
        assertEquals("Updated description", updatedAccount.getDescription());
    }

    @Test
    public void testDeleteAccount() {
        // Arrange
        Long accountId = 1L;

        // Act
        accountService.deleteAccount(accountId);

        // Assert
        verify(accountRepository, times(1)).deleteById(accountId);
    }

    @Test
    public void testGetAccountsByCustomerId() {
        // Arrange
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);

        Account account = Account.builder()
                .accountNumber("12345678")
                .accountName("John Doe")
                .accountType(AccountType.SAVING)
                .balance(1000.0)
                .currency(Currency.USD)
                .customer(customer)
                .description("Savings Account")
                .build();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(accountRepository.findByCustomer(customer)).thenReturn(List.of(account));

        // Act
        List<Account> accounts = accountService.getAccountsByCustomerId(customerId);

        // Assert
        assertNotNull(accounts);
        assertEquals(1, accounts.size());
        assertEquals("John Doe", accounts.get(0).getAccountName());
    }


}