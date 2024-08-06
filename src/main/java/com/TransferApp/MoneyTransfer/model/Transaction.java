package com.TransferApp.MoneyTransfer.model;

import com.TransferApp.MoneyTransfer.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Currency;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_account_id", referencedColumnName ="id")
    private Account fromAccount;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_account_id", referencedColumnName ="id")
    private Account toAccount;

     @Column( nullable = false)
    private double amount;
     private double convertedAmount;
    @Column( nullable = false)
    private TransactionType transactionType;
    private String description;
    private String status;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Version
    private Long version;

}