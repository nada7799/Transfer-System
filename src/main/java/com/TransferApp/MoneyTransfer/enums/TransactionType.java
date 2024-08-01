package com.TransferApp.MoneyTransfer.enums;

import lombok.Getter;

@Getter
public enum TransactionType {
    DEPOSIT,
    WITHDRAWAL,
    INTERNAL_TRANSFER,
    EXTERNAL_TRANSFER,
    PAYMENT,
    REFUND,
    DEBIT, CREDIT, CURRENCY_CONVERSION
}
