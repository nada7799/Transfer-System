package com.TransferApp.MoneyTransfer.controller;


import com.TransferApp.MoneyTransfer.dto.createCustomerDto;
import com.TransferApp.MoneyTransfer.dto.customerDTO;
import com.TransferApp.MoneyTransfer.dto.loginRequestDTO;
import com.TransferApp.MoneyTransfer.dto.loginResponseDTO;
import com.TransferApp.MoneyTransfer.exception.CustomerAlreadyExistsException;
import com.TransferApp.MoneyTransfer.service.sercurity.IAuthenticator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


// we made a separate controller for the register and login
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Validated

public class AuthController {

    private final IAuthenticator authenticatorService;

    @PostMapping("/register")
    public customerDTO register(@RequestBody @Valid createCustomerDto createCustomerDto) throws CustomerAlreadyExistsException {
        return this.authenticatorService.register(createCustomerDto);
    }

    @PostMapping("/login")
    public loginResponseDTO login(@RequestBody @Valid loginRequestDTO loginRequestDTO)  {
        return this.authenticatorService.login(loginRequestDTO);
    }


    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String token) {
        this.authenticatorService.logout(token);
    }
}
