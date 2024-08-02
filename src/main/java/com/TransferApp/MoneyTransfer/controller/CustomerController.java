package com.TransferApp.MoneyTransfer.controller;

import com.TransferApp.MoneyTransfer.dto.customerDTO;
import com.TransferApp.MoneyTransfer.dto.updateCustomerDTO;
import com.TransferApp.MoneyTransfer.exception.CustomerNotFoundException;
import com.TransferApp.MoneyTransfer.service.ICustomer;
import com.TransferApp.MoneyTransfer.service.customerService;
import com.TransferApp.MoneyTransfer.utils.HasUserAccess;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Validated

public class CustomerController {
    private final ICustomer customerService;


    @GetMapping("/{id}")
    @HasUserAccess
    public customerDTO getCustomerById(@PathVariable long id) throws CustomerNotFoundException {
        return customerService.getCustomerById(id);
    }



    @PutMapping("/{id}")
    @HasUserAccess
    public customerDTO updateCustomer(@PathVariable long id ,@RequestBody @Valid updateCustomerDTO updateCustomerDTO) throws CustomerNotFoundException{
        return customerService.updateCustomer(id,updateCustomerDTO);
    }



    @DeleteMapping("/{id}")
    @HasUserAccess
    public void deleteCustomer(@PathVariable long id)throws CustomerNotFoundException{
        customerService.deleteCustomer(id);
    }


}
