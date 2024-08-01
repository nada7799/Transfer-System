package com.TransferApp.MoneyTransfer.dto;

import com.TransferApp.MoneyTransfer.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateAccountDto {

    @NotBlank
    private String accountNumber;

    @NotNull
    private AccountType accountType;

    // @NotNull
    // private Currency currency;

    @NotBlank
    private String accountName;

    @NotBlank
    private String accountDescription;

    public String getAccountNumber() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAccountNumber'");
    }

    public AccountType getAccountType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAccountType'");
    }

}