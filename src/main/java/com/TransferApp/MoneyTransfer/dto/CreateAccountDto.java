package com.TransferApp.MoneyTransfer.dto;

import com.TransferApp.MoneyTransfer.enums.AccountType;
import com.TransferApp.MoneyTransfer.enums.Currency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateAccountDto {


    @NotNull
    private AccountType accountType;

    private double balance;

    private Currency currency;

    @NotBlank
    private String accountDescription;



}