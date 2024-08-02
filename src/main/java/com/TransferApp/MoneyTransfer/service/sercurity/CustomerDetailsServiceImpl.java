package com.TransferApp.MoneyTransfer.service.sercurity;


import com.TransferApp.MoneyTransfer.exception.CustomerNotFoundException;
import com.TransferApp.MoneyTransfer.model.Account;
import com.TransferApp.MoneyTransfer.model.Customer;
import com.TransferApp.MoneyTransfer.reporsitory.AccountRepository;
import com.TransferApp.MoneyTransfer.reporsitory.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


// this class is to get customer from the database by his email and put its info in a customerDetailsImp object
@Service
@RequiredArgsConstructor
public class CustomerDetailsServiceImpl implements UserDetailsService {
    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = this.customerRepository.findByEmail(username).orElseThrow(() -> new CustomerNotFoundException("customer not found with email: "+ username));
        return CustomerDetailsImpl.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .password(customer.getPassword())
                .build();
    }
}
