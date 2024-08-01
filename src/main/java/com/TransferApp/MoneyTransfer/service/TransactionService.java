package com.TransferApp.MoneyTransfer.service;

import com.TransferApp.MoneyTransfer.dto.TransferRequestDTO;
import com.TransferApp.MoneyTransfer.enums.TransactionType;
import com.TransferApp.MoneyTransfer.model.Account;
import com.TransferApp.MoneyTransfer.model.Transaction;
import com.TransferApp.MoneyTransfer.reporsitory.AccountRepository;
import com.TransferApp.MoneyTransfer.reporsitory.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService  implements ITransaction{

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public void transferMoney(TransferRequestDTO transferRequest) {
        Account sourceAccount = accountRepository.findById(transferRequest.getSourceAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Source account not found"));
        Account destinationAccount = accountRepository.findById(transferRequest.getDestinationAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Destination account not found"));

        if (sourceAccount.getBalance() < transferRequest.getAmount()) {
            throw new IllegalArgumentException("Insufficient funds in source account");
        }
         // do currency conversion here
        // Update source account balance
        sourceAccount.setBalance(sourceAccount.getBalance() - transferRequest.getAmount());
        accountRepository.save(sourceAccount);

        // Update destination account balance
        destinationAccount.setBalance(destinationAccount.getBalance() + transferRequest.getAmount());
        accountRepository.save(destinationAccount);

        // Record the transactions
        recordTransaction(sourceAccount,destinationAccount, transferRequest.getAmount(), transferRequest.getTransactionType(), "Transfer from " + sourceAccount.getAccountName() + " to " + destinationAccount.getAccountName());

    }
    private void recordTransaction(Account sourceAccount,Account destinationAccount, Double amount, TransactionType type, String description) {
        Transaction transaction = Transaction.builder()
                .amount(amount)
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
    public List<Transaction> getTransactionsByAccountId(Long accountId) {
                return transactionRepository.findByFromAccountIdOrToAccountId(accountId, accountId).get();
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
