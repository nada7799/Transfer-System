package com.TransferApp.MoneyTransfer.service;

import com.TransferApp.MoneyTransfer.dto.TransferRequestDTO;
import com.TransferApp.MoneyTransfer.enums.Currency;
import com.TransferApp.MoneyTransfer.enums.TransactionType;
import com.TransferApp.MoneyTransfer.model.Account;
import com.TransferApp.MoneyTransfer.model.Transaction;
import com.TransferApp.MoneyTransfer.reporsitory.AccountRepository;
import com.TransferApp.MoneyTransfer.reporsitory.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService  implements ITransaction{

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CurrencyConverter currencyConversionService;
    private final InputSanitizer inputSanitizer;
    @Override
    @Transactional
    public void transferMoney(TransferRequestDTO transferRequest) {
        Long sanitizedFromAccountId = inputSanitizer.sanitizeLong(transferRequest.getSourceAccountId().toString());
        String sanitizedToAccountNumber = inputSanitizer.sanitize(transferRequest.getAccountNumber());
        Double sanitizedAmount = inputSanitizer.sanitizeDouble(transferRequest.getAmount().toString());
        Account sourceAccount = accountRepository.findById(sanitizedFromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Source account not found"));
        Account destinationAccount = accountRepository.findByAccountNumber(sanitizedToAccountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Destination account not found"));

        if (sourceAccount.getBalance() < sanitizedAmount) {
            throw new IllegalArgumentException("Insufficient funds in source account");
        }
        Currency sourceCurrency = sourceAccount.getCurrency();
        Currency destinationCurrency = destinationAccount.getCurrency();
        double convertedAmount = sanitizedAmount;

        // If the source and destination currencies are different, do the currency conversion
        if (!sourceCurrency.equals(destinationCurrency)) {
            double conversionRate = currencyConversionService.getConversionRate(sourceCurrency.toString(), destinationCurrency.toString());
            convertedAmount =sanitizedAmount * conversionRate;
        }

        // do currency conversion here
        // Update source account balance
        sourceAccount.setBalance(sourceAccount.getBalance() - sanitizedAmount);
        accountRepository.save(sourceAccount);

        // Update destination account balance
        destinationAccount.setBalance(destinationAccount.getBalance() + convertedAmount);
        accountRepository.save(destinationAccount);

        // Record the transactions
        // Record the transactions
        recordTransaction(sourceAccount, destinationAccount, transferRequest.getAmount(), convertedAmount, transferRequest.getTransactionType(), "Transfer from " + sourceAccount.getAccountName() + " to " + destinationAccount.getAccountName());
    }

    private void recordTransaction(Account sourceAccount, Account destinationAccount, Double sourceAmount, Double destinationAmount, TransactionType type, String description) {
        Transaction transaction = Transaction.builder()
                .amount(sourceAmount)
                .convertedAmount(destinationAmount)
                .fromAccount(sourceAccount)
                .toAccount(destinationAccount)
                .transactionType(type)
                .description(description)
                .status("SUCCESS")
                .build();
        transactionRepository.save(transaction);
    }

    public Transaction createTransaction(Long fromAccountId, Long toAccountId, double amount, TransactionType transactionType) {
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("From Account not found"));
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("To Account not found"));

        Transaction transaction = new Transaction();
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAmount(amount);
        transaction.setTransactionType(transactionType);

        return transactionRepository.save(transaction);
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }
   @Transactional
    public Page<Transaction> getTransactionsByAccountId(Long accountId) {
       String sanitizedFromAccountId = inputSanitizer.sanitize(accountId.toString());
       long fromAccountIdInt = Long.parseLong(sanitizedFromAccountId);

       Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "createdAt"));
       return transactionRepository.findByFromAccountIdOrToAccountId(fromAccountIdInt, fromAccountIdInt, pageable);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
