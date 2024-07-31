package com.TransferApp.MoneyTransfer.dto;


import com.TransferApp.MoneyTransfer.enums.GenderEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class createCustomerDto  {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

    private GenderEnum gender;
    @Email
    private String email;
    @NotNull
    private String phoneNumber;

    private String address;

    private String nationality;
    @NotNull
    private String nationalNumber;
    @DateTimeFormat
    @NotNull
    private LocalDate dateOfBirth;

     @NotNull
    private String password;
}
