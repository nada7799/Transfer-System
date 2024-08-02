package com.TransferApp.MoneyTransfer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "favorite_recipient")
public class FavoriteRecipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;  // The customer who adds the favorite recipient

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private Customer recipient; // The customer who is the favorite recipient

    private String nickname; // Optional field to give a nickname to the favorite recipient
}
