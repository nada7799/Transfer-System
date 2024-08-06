package com.TransferApp.MoneyTransfer.reporsitory;


import com.TransferApp.MoneyTransfer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
      Optional<Customer> findByEmail(String phoneNumber);
    @Query("SELECT c FROM Customer c JOIN c.accounts a WHERE a.accountNumber = :accountNumber")
    Optional<Customer> findCustomerByAccountNumber(@Param("accountNumber") String accountNumber);
    Boolean  existsByEmail(String email);
    Boolean existsByPhoneNumber(String phoneNumber);
    Boolean existsByNationalIdNumber(String nationalId);
}
