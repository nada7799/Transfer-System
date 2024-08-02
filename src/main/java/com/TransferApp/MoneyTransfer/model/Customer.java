package com.TransferApp.MoneyTransfer.model;


import com.TransferApp.MoneyTransfer.dto.customerDTO;
import com.TransferApp.MoneyTransfer.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.Set;

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
    @Enumerated(value = EnumType.STRING)
    private GenderEnum gender;

    @Column(nullable = false, unique = true)
    private String phoneNumber;


    @Column(nullable = false)
    private String email;
    @NotNull
    private String password;
    private String address;

   private String nationality;
    @Column(nullable = false, unique = true)
   private String nationalIdNumber;
    @Column(nullable = false)
     private LocalDate dateOfBirth;

    @CreationTimestamp
    private LocalDateTime creationTime;
    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private Set<Account> accounts;




    public customerDTO toDTO(){
        return customerDTO.builder()
                .id(this.id)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .gender(this.gender)
                .phoneNumber(this.phoneNumber)
                .address(this.address)
                .nationality(this.nationality)
                .nationalNumber(this.nationalIdNumber)
                .dateOfBirth(this.dateOfBirth)
                .email(this.email)
                .build();
    }

}
