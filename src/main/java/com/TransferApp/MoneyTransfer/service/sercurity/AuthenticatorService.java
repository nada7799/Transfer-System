package com.TransferApp.MoneyTransfer.service.sercurity;
import com.TransferApp.MoneyTransfer.dto.*;
import com.TransferApp.MoneyTransfer.enums.AccountType;
import com.TransferApp.MoneyTransfer.enums.Currency;
import com.TransferApp.MoneyTransfer.exception.CustomerAlreadyExistsException;
import com.TransferApp.MoneyTransfer.model.Account;
import com.TransferApp.MoneyTransfer.model.Customer;
import com.TransferApp.MoneyTransfer.reporsitory.AccountRepository;
import com.TransferApp.MoneyTransfer.reporsitory.CustomerRepository;
import com.TransferApp.MoneyTransfer.service.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class AuthenticatorService implements IAuthenticator {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final TokenBlacklistService tokenBlacklistService;


    @Override
    @Transactional
    public accountDTO register(createCustomerDto createCustomerDto) throws CustomerAlreadyExistsException
    {
        if(this.customerRepository.existsByEmail(createCustomerDto.getEmail()) ) {
            throw new CustomerAlreadyExistsException(String.format("Customer with email %s already exists", createCustomerDto.getEmail()));
        }
        if(this.customerRepository.existsByNationalIdNumber(createCustomerDto.getNationalNumber())) {
            throw new CustomerAlreadyExistsException(String.format("Customer with national Id number %s already exists", createCustomerDto.getNationalNumber()));
        }
        if(this.customerRepository.existsByPhoneNumber(createCustomerDto.getPhoneNumber())) {
            throw new CustomerAlreadyExistsException(String.format("Customer with phone number %s already exists", createCustomerDto.getPhoneNumber()));
        }





        Customer customer =   Customer.builder()
                .firstName(createCustomerDto.getFirstName())
                .lastName(createCustomerDto.getLastName())
                .gender(createCustomerDto.getGender())
                .phoneNumber(createCustomerDto.getPhoneNumber())
                .address(createCustomerDto.getAddress())
                .nationality(createCustomerDto.getNationality())
                .nationalIdNumber(createCustomerDto.getNationalNumber())
                .email(createCustomerDto.getEmail())
                .password(passwordEncoder.encode(createCustomerDto.getPassword()))
                .dateOfBirth(createCustomerDto.getDateOfBirth())
                .build();
        Account account = Account.builder()
                .accountNumber(String.valueOf(new SecureRandom().nextInt(100000000)))
                .accountName(createCustomerDto.getFirstName()).accountType(AccountType.SAVING)
                .balance(10000)
                .currency(Currency.EGP)
                .customer(customerRepository.save(customer))
                .build();

        accountRepository.save(account);
        return  account.toDTO();
    }

    @Override
    public loginResponseDTO login(loginRequestDTO loginRequestDTO) {
        Authentication authentication;
        // here we did authenticate
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken((loginRequestDTO.getEmail()), loginRequestDTO.getPassword()));
        }
        catch (AuthenticationException e){
            return loginResponseDTO.builder().statusCode(HttpStatus.UNAUTHORIZED).message(e.getMessage()).build();
        }
        //we authenticate and put the context across spring boot to access it from anywhere
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //here we generate token
        String jwt = jwtUtils.generateToken(authentication);

        return loginResponseDTO.builder()
                .token(jwt)
                .statusCode(HttpStatus.ACCEPTED)
                .tokenType("Bearer")
                .message("login successful")
                .build();

    }

    @Override
    public ResponseEntity<Void> logout(String token) {
        String jwtToken = token.replace("Bearer ", "");
         tokenBlacklistService.blackListToken(jwtToken);
         return ResponseEntity.ok().build();
    }
}
