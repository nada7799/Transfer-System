package com.TransferApp.MoneyTransfer.exception.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ErrorDetails {
    private String message;
    private LocalDateTime timestamp;
    private String details;
    private HttpStatus  httpStatus;
}
