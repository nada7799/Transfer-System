package com.TransferApp.MoneyTransfer.service;


import com.TransferApp.MoneyTransfer.dto.customerDTO;
import com.TransferApp.MoneyTransfer.dto.updateCustomerDTO;
import com.TransferApp.MoneyTransfer.exception.CustomerNotFoundException;

public interface ICustomer {

    customerDTO updateCustomer(long id, updateCustomerDTO updateCustomerDTO) throws CustomerNotFoundException;
    void deleteCustomer(long id)throws CustomerNotFoundException;
    customerDTO getCustomerById(long id) throws CustomerNotFoundException;



}
