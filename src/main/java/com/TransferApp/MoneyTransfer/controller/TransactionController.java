package com.TransferApp.MoneyTransfer.controller;


import com.TransferApp.MoneyTransfer.dto.TransferRequestDTO;
import com.TransferApp.MoneyTransfer.enums.TransactionType;
import com.TransferApp.MoneyTransfer.model.Transaction;
import com.TransferApp.MoneyTransfer.service.ITransaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
@Tag(name = "Transaction" , description = "The Transactions API")
public class TransactionController {

    private final ITransaction transactionService;
    @Operation(summary = "endpoint for transferring money from account to the other with currency conversion maintained")
    @ApiResponse(
            responseCode = "200", description = "Successful transfer"
    )
    @PostMapping("/transfer")
    public ResponseEntity<Void> transferMoney(@RequestBody TransferRequestDTO transferRequest) {
        transactionService.transferMoney(transferRequest);
        return ResponseEntity.ok().build();
}


//    @PostMapping
//    public ResponseEntity<Transaction> createTransaction(
//            @RequestParam Long fromAccountId,
//            @RequestParam Long toAccountId,
//            @RequestParam double amount,
//            @RequestParam TransactionType transactionType) {
//        Transaction transaction = transactionService.createTransaction(fromAccountId, toAccountId, amount, transactionType);
//        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
//    }
      @Operation(summary = "this gets the transaction with id --> id to display its info for a certain transaction")
    @ApiResponse(
            responseCode = "200",
            description = "Successful response with transaction information",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Transaction.class)
            )
    )
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        Optional<Transaction> transaction = transactionService.getTransactionById(id);
        return transaction.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
      @Operation(summary = "this gets all the transactions that belong to an account with id --> accountId")
    @ApiResponse(
            responseCode = "200",
            description = "Successful response with transaction information",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Transaction.class)
            )
    )
    @GetMapping("/account/{accountId}")
    public ResponseEntity<Page<Transaction>> getTransactionsByAccountId(@PathVariable Long accountId) {
        Page<Transaction> transactions = transactionService.getTransactionsByAccountId(accountId);
        return ResponseEntity.ok(transactions);
    }

//    @GetMapping
//    public ResponseEntity<List<Transaction>> getAllTransactions() {
//        List<Transaction> transactions = transactionService.getAllTransactions();
//        return ResponseEntity.ok(transactions);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
