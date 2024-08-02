package com.TransferApp.MoneyTransfer.service;

import com.TransferApp.MoneyTransfer.dto.TransferRequestDTO;
import com.TransferApp.MoneyTransfer.enums.TransactionType;
import com.TransferApp.MoneyTransfer.model.Transaction;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ITransaction {
    public Transaction createTransaction(Long fromAccountId, Long toAccountId, double amount, TransactionType transactionType);
    public Optional<Transaction> getTransactionById(Long id);
   public Page<Transaction> getTransactionsByAccountId(Long accountId);
    public List<Transaction> getAllTransactions();
    public void deleteTransaction(Long id);
    public void transferMoney(TransferRequestDTO transferRequest);
}
