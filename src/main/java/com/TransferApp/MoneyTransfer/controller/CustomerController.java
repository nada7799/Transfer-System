package com.TransferApp.MoneyTransfer.controller;

import com.TransferApp.MoneyTransfer.dto.accountDTO;
import com.TransferApp.MoneyTransfer.dto.customerDTO;
import com.TransferApp.MoneyTransfer.dto.updateCustomerDTO;
import com.TransferApp.MoneyTransfer.exception.CustomerNotFoundException;
import com.TransferApp.MoneyTransfer.model.FavoriteRecipient;
import com.TransferApp.MoneyTransfer.service.FavoriteRecipientService;
import com.TransferApp.MoneyTransfer.service.ICustomer;
import com.TransferApp.MoneyTransfer.service.customerService;
import com.TransferApp.MoneyTransfer.utils.HasUserAccess;
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

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Validated
@Tag(name = "Customer", description = "The Customer API")
public class CustomerController {
    private final ICustomer customerService;



    @Operation(description = "this endpoint gets the customer information ")
    @ApiResponse(
            responseCode = "200",
            description = "customer details is returned",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = customerDTO.class)
            )
    )
    @GetMapping("/{id}")
    @HasUserAccess
    public customerDTO getCustomerById(@PathVariable long id) throws CustomerNotFoundException {
        return customerService.getCustomerById(id);
    }


    @Operation(summary = "this updates customer details")
    @ApiResponse(
            responseCode = "200",
            description = "customer details is updated",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = customerDTO.class)
            )
    )
    @PutMapping("/{id}")
    @HasUserAccess
    public customerDTO updateCustomer(@PathVariable long id ,@RequestBody @Valid updateCustomerDTO updateCustomerDTO) throws CustomerNotFoundException{
        return customerService.updateCustomer(id,updateCustomerDTO);
    }


      @Operation(summary = "this deletes a customer with all of his accounts")
    @ApiResponse(
            responseCode = "200",
            description = "customer is deleted"
    )
    @DeleteMapping("/{id}")
    @HasUserAccess
    public void deleteCustomer(@PathVariable long id)throws CustomerNotFoundException{
        customerService.deleteCustomer(id);
    }


}
