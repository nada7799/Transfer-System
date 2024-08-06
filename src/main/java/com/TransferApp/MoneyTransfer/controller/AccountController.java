package com.TransferApp.MoneyTransfer.controller;


import com.TransferApp.MoneyTransfer.dto.CreateAccountDto;
import com.TransferApp.MoneyTransfer.dto.UpdateAccountDto;
import com.TransferApp.MoneyTransfer.dto.accountDTO;
import com.TransferApp.MoneyTransfer.enums.Currency;
import com.TransferApp.MoneyTransfer.exception.CustomerAlreadyExistsException;
import com.TransferApp.MoneyTransfer.model.Account;
import com.TransferApp.MoneyTransfer.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
@Tag(name = "Account", description = "account management APIs")
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }
      @Operation(summary = "this gets the account with id --> id to show its data")
    @ApiResponse(
            responseCode = "200",
            description = "Successful response with account information",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Account.class)
            )
    )
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable @Positive @NotNull Long id) {
        return accountService.getAccountById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



     @Operation(summary= "this creates an account for a customer with id --> id")
    @ApiResponse(
            responseCode = "201",
            description = "Successful response with account information",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Account.class)
            )
    )
    @PostMapping("customer/{id}")
    public ResponseEntity<Account> createAccount(@Valid @RequestBody @NotNull CreateAccountDto createAccountDTO,@PathVariable @NotNull @Positive Long id) throws CustomerAlreadyExistsException {

        Account createdAccount = accountService.createAccount(createAccountDTO,id);
        return ResponseEntity.status(201).body(createdAccount);
    }



    @Operation(summary = "this gets all the accounts that belong to a customer with id --> customerId")
    @ApiResponse(
            responseCode = "200",
            description = "Successful response with account information",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Account.class)
            )
    )
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Account>> getAccountsByCustomerId(@PathVariable Long customerId) {
        List<Account> accounts = accountService.getAccountsByCustomerId(customerId);
        return ResponseEntity.ok(accounts);
}


        @Operation(summary = "this updates the account that has id --> id")
    @ApiResponse(
            responseCode = "200",
            description = "Successful response with updated account information",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Account.class)
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @Valid @RequestBody UpdateAccountDto updateAccountDTO) {
        Account updatedAccount = accountService.updateAccount(id, updateAccountDTO);
        return ResponseEntity.ok(updatedAccount);
    }


      @Operation(description = "this deletes the account that has id --> id")
    @ApiResponse(
            responseCode = "204",
            description = "Successful response with no content"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "this changes the currency of a the account that has id --> id and changes the balance accordingly")
    @ApiResponse(
            responseCode = "200",
            description = "Successful response with updated account information",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = accountDTO.class)
            )
    )
    @PostMapping("currency/{id}/{currency}")
    public ResponseEntity<accountDTO> changeCurrency(@PathVariable Long id, @PathVariable Currency currency) {
        accountDTO updatedAccount = accountService.changeCurrency(id, currency);
        return ResponseEntity.ok(updatedAccount);
    }
}