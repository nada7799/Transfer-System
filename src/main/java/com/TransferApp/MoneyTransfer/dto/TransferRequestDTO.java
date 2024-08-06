package com.TransferApp.MoneyTransfer.dto;

import com.TransferApp.MoneyTransfer.enums.AccountType;
import com.TransferApp.MoneyTransfer.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferRequestDTO {
    @NotNull(message = "Source account ID cannot be null")
    @Positive(message = "From Account ID must be positive")
    private Long sourceAccountId;
    @NotNull(message = "destination account Number cannot be null")
    private String accountNumber;
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "amount must be positive")
    private Double amount;
    @NotNull(message = "Transaction type cannot be null")
    private TransactionType  transactionType;
}
