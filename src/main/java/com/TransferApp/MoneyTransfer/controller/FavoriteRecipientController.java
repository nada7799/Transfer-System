package com.TransferApp.MoneyTransfer.controller;

import com.TransferApp.MoneyTransfer.model.FavoriteRecipient;
import com.TransferApp.MoneyTransfer.service.FavoriteRecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteRecipientController {

    @Autowired
    private FavoriteRecipientService favoriteRecipientService;

    @PostMapping("{customerId}/{recipientId}")
    public FavoriteRecipient addFavoriteRecipient(
            @PathVariable long customerId,
            @PathVariable long recipientId) {

        return favoriteRecipientService.addFavoriteRecipient(customerId, recipientId);
    }

    @GetMapping("/{customerId}")
    public List<FavoriteRecipient> getFavoriteRecipients(@PathVariable long customerId) {
        return favoriteRecipientService.getFavoriteRecipients(customerId);
    }

    @DeleteMapping("/{id}/{customerId}")
    public void removeFavoriteRecipient(
            @PathVariable long id,
            @PathVariable long customerId) {

        favoriteRecipientService.removeFavoriteRecipient(id, customerId);
    }
}
