package com.TransferApp.MoneyTransfer.reporsitory;

import com.TransferApp.MoneyTransfer.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<List<Transaction>> findByFromAccountIdOrToAccountId(Long fromAccountId, Long toAccountId);
}
