package com.TransferApp.MoneyTransfer.dto;

import com.TransferApp.MoneyTransfer.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class customerDTO implements Serializable {
    private  long id;
    private   String firstName;

    private   String lastName;

    private GenderEnum gender;
     private String email;

    private   String phoneNumber;

    private   String address;

    private  String nationality;

    private   String nationalNumber;

    private   LocalDate dateOfBirth;

}
