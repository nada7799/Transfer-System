package com.TransferApp.MoneyTransfer.model;


import com.TransferApp.MoneyTransfer.dto.customerDTO;
import com.TransferApp.MoneyTransfer.enums.GenderEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "customer")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;

    private GenderEnum gender;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String phoneNumber;

    private String address;

   private String nationality;
    @Column(nullable = false, unique = true)
   private String nationalIdNumber;
    @Column(nullable = false)
     private LocalDate dateOfBirth;
    @NotNull
    private String password;
    @CreationTimestamp
    private LocalDateTime creationTime;
    @OneToOne
   private Account account;

    public customerDTO toDTO(){
        return customerDTO.builder()
                .id(this.id)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .gender(this.gender)
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .address(this.address)
                .nationality(this.nationality)
                .nationalNumber(this.nationalIdNumber)
                .dateOfBirth(this.dateOfBirth)
                .accountDTO(this.account.toDTO())
                .build();
    }

}
