package com.TransferApp.MoneyTransfer.service;

import com.TransferApp.MoneyTransfer.model.Customer;
import com.TransferApp.MoneyTransfer.model.FavoriteRecipient;

import com.TransferApp.MoneyTransfer.reporsitory.CustomerRepository;
import com.TransferApp.MoneyTransfer.reporsitory.FavoriteRecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FavoriteRecipientService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private FavoriteRecipientRepository favoriteRecipientRepository;
    public FavoriteRecipient addFavoriteRecipient(long customerId, long recipientId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Customer recipient = customerRepository.findById(recipientId)
                .orElseThrow(() -> new RuntimeException("Recipient not found"));

        FavoriteRecipient favoriteRecipient = FavoriteRecipient.builder()
                .customer(customer)
                .recipient(recipient)
                .build();
        return favoriteRecipientRepository.save(favoriteRecipient);
    }

    public List<FavoriteRecipient> getFavoriteRecipients(long customerId) {
        return favoriteRecipientRepository.findByCustomerId(customerId);
    }

    public void removeFavoriteRecipient(long id, long customerId) {
        favoriteRecipientRepository.deleteByIdAndCustomerId(id, customerId);
    }
}