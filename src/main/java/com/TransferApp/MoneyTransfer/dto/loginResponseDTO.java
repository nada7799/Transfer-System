package com.TransferApp.MoneyTransfer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class loginResponseDTO {
    private String token;
    private String tokenType;
    private String message;
    private HttpStatus statusCode;

}
