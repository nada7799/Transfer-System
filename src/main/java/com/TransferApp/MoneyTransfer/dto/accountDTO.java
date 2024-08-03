package com.TransferApp.MoneyTransfer.dto;


import com.TransferApp.MoneyTransfer.enums.AccountType;
import com.TransferApp.MoneyTransfer.enums.Currency;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class accountDTO {
    private final long id;

    private final String accountNumber;

    private final String accountName;

    private final double balance;


    private final Currency currency;

    private final AccountType accountType;

    private final LocalDateTime createdAt;


    private final LocalDateTime updatedAt;

    private customerDTO customer;

}
