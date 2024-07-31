package com.TransferApp.MoneyTransfer.dto;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Data
@Builder
public class updateCustomerDTO {

    private String firstName;

    private String lastName;

    @Email
    private String email;

    private String phoneNumber;

    private String address;

    private String nationality;

    @DateTimeFormat

    private LocalDate dateOfBirth;



    private String password;
}
