package com.TransferApp.MoneyTransfer.model;


import com.TransferApp.MoneyTransfer.dto.accountDTO;
import com.TransferApp.MoneyTransfer.enums.AccountType;
import com.TransferApp.MoneyTransfer.enums.Currency;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false , unique = true)
    private String accountNumber;
    private String accountName;
    @Column(nullable = false)
    private double balance;
    @Enumerated(value = EnumType.STRING)
    private Currency currency;
    @Enumerated(value = EnumType.STRING)
    private AccountType accountType;

     @Builder.Default
   private boolean isActive = true;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "parent_account_id")
    @JsonIgnore
    private Account parentAccount;

    @OneToMany(mappedBy = "parentAccount")
    @JsonIgnore
    private Set<Account> subAccounts;


    @OneToMany(mappedBy = "fromAccount", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Transaction> sentTransactions;


    @OneToMany(mappedBy = "toAccount", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Transaction> receivedTransactions;


    private String description;


    public accountDTO toDTO(){
       return accountDTO.builder()
               .id(this.id)
               .accountName(this.accountName)
                .accountNumber(this.accountNumber)
                .accountType(this.accountType)
                .balance(this.balance)
                .currency(this.currency)
               .createdAt(this.createdAt)
               .updatedAt(this.updatedAt)
               .customer(this.customer.toDTO())
                .build();
    }
}
