package com.TransferApp.MoneyTransfer.exception.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ViolationErrors {
    private String fieldName;
    private String message;
}
