package com.TransferApp.MoneyTransfer.controller;


import com.TransferApp.MoneyTransfer.dto.*;
import com.TransferApp.MoneyTransfer.exception.CustomerAlreadyExistsException;
import com.TransferApp.MoneyTransfer.model.Account;
import com.TransferApp.MoneyTransfer.service.sercurity.IAuthenticator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


// we made a separate controller for the register and login
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Validated
@Tag(name = "authorization", description = "APIs for register , login , logout")
public class AuthController {

    private final IAuthenticator authenticatorService;
    @Operation(summary = "this registers a new user and makes an account for him with initial balance that is assumed ")
    @ApiResponse(
            responseCode = "200",
            description = "user registered successfully",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = accountDTO.class)
            )
    )
    @PostMapping("/register")
    public accountDTO register(@RequestBody @Valid createCustomerDto createCustomerDto) throws CustomerAlreadyExistsException {
        return this.authenticatorService.register(createCustomerDto);
    }

    @Operation(summary = "this logs in a user and returns a token that is sent with every request after that")
    @ApiResponse(
            responseCode = "200",
            description = "user logged in successfully",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = loginResponseDTO.class)
            )
    )
    @PostMapping("/login")
    public loginResponseDTO login(@RequestBody @Valid loginRequestDTO loginRequestDTO)  {
        return this.authenticatorService.login(loginRequestDTO);
    }

  @Operation(summary = "this logs out a user by invalidating the token")
    @ApiResponse(
            responseCode = "200",
            description = "user logged out successfully"
    )
    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String token) {
        this.authenticatorService.logout(token);
    }
}
