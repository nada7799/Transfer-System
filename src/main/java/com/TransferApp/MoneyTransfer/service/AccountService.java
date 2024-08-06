package com.TransferApp.MoneyTransfer.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CurrencyConverter  currencyConverter;
    @Autowired
    private InputSanitizer inputSanitizer;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(Long id) {
        long sanitizedId = inputSanitizer.sanitizeLong(id.toString());
        return accountRepository.findById(sanitizedId);
    }

    public Account createAccount(CreateAccountDto createAccountDTO, long id) throws CustomerAlreadyExistsException {
        String sanitizedCurrency = inputSanitizer.sanitize(createAccountDTO.getCurrency().toString());
        String sanitizedAmount = inputSanitizer.sanitize(createAccountDTO.getAccountType().toString());
         Currency currency = Currency.valueOf(sanitizedCurrency);
         AccountType accountType = AccountType.valueOf(sanitizedAmount);

        Customer  customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        Account account =  Account.builder()
                .accountNumber(String.valueOf(new SecureRandom().nextInt(100000000)))
                .accountName(customer.getFirstName() + " " + customer.getLastName()).accountType(AccountType.SAVING)
                .balance(createAccountDTO.getBalance())
                .currency(createAccountDTO.getCurrency())
                .customer(customer)
                .description(createAccountDTO.getAccountDescription())
                .build();

        return accountRepository.save(account);
    }

    public Account updateAccount(Long id, UpdateAccountDto updateAccountDTO) {
        return accountRepository.findById(id).map(account -> {
            if (updateAccountDTO.getAccountType() != null) {
                account.setAccountType(updateAccountDTO.getAccountType());
            }
            // if (updateAccountDTO.getCurrency() != null) {
            // account.setCurrency(updateAccountDTO.getCurrency());
            // }
            if (updateAccountDTO.getAccountName() != null) {
                account.setAccountName(updateAccountDTO.getAccountName());
            }
            if (updateAccountDTO.getAccountDescription() != null) {
                account.setDescription(updateAccountDTO.getAccountDescription());
            }
            if (updateAccountDTO.getActive() != null) {
                account.setActive(updateAccountDTO.getActive());
            }
            return accountRepository.save(account);
        }).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public List<Account> getAccountsByCustomerId(Long customerId) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            return accountRepository.findByCustomer(customer);
        } else {
            throw new CustomerNotFoundException("Customer not found");
}
}
     public  accountDTO changeCurrency(Long accountId, Currency newCurrency){
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
         double conversionRate = currencyConverter.getConversionRate(account.getCurrency().toString(),newCurrency.toString());
         double convertedAmount = account.getBalance() * conversionRate;
        account.setCurrency(newCurrency);
        account.setBalance(convertedAmount);
        return accountRepository.save(account).toDTO();
     }
}