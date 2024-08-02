package com.TransferApp.MoneyTransfer.reporsitory;

import com.TransferApp.MoneyTransfer.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findByFromAccountIdOrToAccountId(Long fromAccountId, Long toAccountId, Pageable pageable);
}
