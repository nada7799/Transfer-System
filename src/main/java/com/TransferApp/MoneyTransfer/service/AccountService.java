package com.TransferApp.MoneyTransfer.service;

import com.TransferApp.MoneyTransfer.dto.CreateAccountDto;
import com.TransferApp.MoneyTransfer.dto.UpdateAccountDto;
import com.TransferApp.MoneyTransfer.model.Account;
import com.TransferApp.MoneyTransfer.reporsitory.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    public Account createAccount(CreateAccountDto createAccountDTO) {
        Account account = new Account();
        account.setAccountNumber(createAccountDTO.getAccountNumber());
        account.setAccountType(createAccountDTO.getAccountType());
        // account.setCurrency(createAccountDTO.getCurrency());
        account.setAccountName(createAccountDTO.getAccountName());
        account.setDescription(createAccountDTO.getAccountDescription());
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
}