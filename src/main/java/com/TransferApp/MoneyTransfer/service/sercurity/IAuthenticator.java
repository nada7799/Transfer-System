package com.TransferApp.MoneyTransfer.service.sercurity;


import com.TransferApp.MoneyTransfer.dto.createCustomerDto;
import com.TransferApp.MoneyTransfer.dto.customerDTO;
import com.TransferApp.MoneyTransfer.dto.loginRequestDTO;
import com.TransferApp.MoneyTransfer.dto.loginResponseDTO;
import com.TransferApp.MoneyTransfer.exception.CustomerAlreadyExistsException;
import com.TransferApp.MoneyTransfer.exception.CustomerNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;


public interface IAuthenticator {
    customerDTO register(createCustomerDto createCustomerDto) throws CustomerAlreadyExistsException;
    loginResponseDTO login(loginRequestDTO loginRequestDTO) throws AuthenticationException;
   ResponseEntity<Void> logout(String token);

}
