package com.TransferApp.MoneyTransfer.dto;

import com.TransferApp.MoneyTransfer.enums.AccountType;
import com.TransferApp.MoneyTransfer.enums.Currency;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CreateAccountDto {

    @Enumerated
    @NotNull(message = "Source account type cannot be null")
    private AccountType accountType;

    @Positive
    @NotNull(message = "Initial balance cannot be null")
    @Positive(message = "Initial balance must be positive")
    private double balance;

    @Enumerated
    @NotNull(message = "Currency cannot be null")
    @Positive(message = "Currency must be positive")
    private Currency currency;

    @NotBlank
    private String accountDescription;



}