package com.TransferApp.MoneyTransfer.dto;

import com.TransferApp.MoneyTransfer.enums.AccountType;
import com.TransferApp.MoneyTransfer.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferRequestDTO {
    @NotNull(message = "Source account ID cannot be null")
    private Long sourceAccountId;
    @NotNull(message = "destination account ID cannot be null")
    private Long destinationAccountId;
    @NotNull(message = "Amount cannot be null")
    private Double amount;
    @NotNull(message = "Transaction type cannot be null")
    private TransactionType  transactionType;
}
