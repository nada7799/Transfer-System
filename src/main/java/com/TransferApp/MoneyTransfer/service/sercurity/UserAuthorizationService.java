package com.TransferApp.MoneyTransfer.service.sercurity;

import com.TransferApp.MoneyTransfer.model.Customer;
import com.TransferApp.MoneyTransfer.reporsitory.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthorizationService {

    @Autowired
    private CustomerRepository customerRepository;

    public boolean canAccessUser(Long id, String username) {
        Optional<Customer> customer = customerRepository.findUserByEmail(username);
        return customer != null && (customer.get().getId() == id);
    }
}
