package com.TransferApp.MoneyTransfer.service;


import com.TransferApp.MoneyTransfer.dto.customerDTO;
import com.TransferApp.MoneyTransfer.dto.updateCustomerDTO;
import com.TransferApp.MoneyTransfer.exception.CustomerNotFoundException;
import com.TransferApp.MoneyTransfer.model.Customer;
import com.TransferApp.MoneyTransfer.reporsitory.CustomerRepository;
import com.TransferApp.MoneyTransfer.service.ICustomer;
import com.TransferApp.MoneyTransfer.utils.HasUserAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class customerService implements ICustomer {
     private  final CustomerRepository customerRepository;
    @HasUserAccess
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public customerDTO updateCustomer(long id, updateCustomerDTO updateCustomerDTO) throws CustomerNotFoundException {
          Customer customer= customerRepository.findById(id)
                  .orElseThrow(()->new CustomerNotFoundException(String.format("Customer with id %d not found",id)));
       if(updateCustomerDTO.getFirstName() != null)
        customer.setFirstName(updateCustomerDTO.getFirstName());

       if(updateCustomerDTO.getLastName() != null)
        customer.setLastName(updateCustomerDTO.getLastName());

       if(updateCustomerDTO.getEmail() != null)
        customer.setEmail(updateCustomerDTO.getEmail());

       if(updateCustomerDTO.getAddress() != null)
        customer.setAddress(updateCustomerDTO.getAddress());

       if(updateCustomerDTO.getNationality() != null)
        customer.setNationality(updateCustomerDTO.getNationality());

       if(updateCustomerDTO.getDateOfBirth() != null)
        customer.setDateOfBirth(updateCustomerDTO.getDateOfBirth());

       if(updateCustomerDTO.getPhoneNumber() != null)
        customer.setPhoneNumber(updateCustomerDTO.getPhoneNumber());

       this.customerRepository.save(customer);
        return  (customer).toDTO();
    }
    @HasUserAccess
    @Override
    public void deleteCustomer(long id)throws CustomerNotFoundException {
        Customer customer= customerRepository.findById(id)
                .orElseThrow(()-> new CustomerNotFoundException(String.format("Customer with id %d not found", id)));
     customerRepository.delete(customer);
    }
    @HasUserAccess
    @Override
    public customerDTO getCustomerById(long id) throws  CustomerNotFoundException {
        Customer customer= customerRepository.findById(id)
                .orElseThrow(()-> new CustomerNotFoundException(String.format("Customer with id %d not found", id)));
        return  customer.toDTO();
    }


}
