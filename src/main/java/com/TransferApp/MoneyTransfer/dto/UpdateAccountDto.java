package com.TransferApp.MoneyTransfer.dto;

import com.TransferApp.MoneyTransfer.enums.AccountType;
import lombok.Data;

@Data
public class UpdateAccountDto {

    @SuppressWarnings("unused")
    private AccountType accountType;
    // @SuppressWarnings("unused")
    // private Currency currency;
    @SuppressWarnings("unused")
    private String accountName;
    @SuppressWarnings("unused")
    private String accountDescription;
    @SuppressWarnings("unused")
    private Boolean active;

}