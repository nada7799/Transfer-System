package com.TransferApp.MoneyTransfer.reporsitory;


import com.TransferApp.MoneyTransfer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findUserByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByPhoneNumber(String phoneNumber);
    Boolean existsByNationalIdNumber(String nationalId);
}
