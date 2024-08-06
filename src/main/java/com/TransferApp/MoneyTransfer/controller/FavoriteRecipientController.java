package com.TransferApp.MoneyTransfer.controller;

import com.TransferApp.MoneyTransfer.dto.customerDTO;
import com.TransferApp.MoneyTransfer.model.FavoriteRecipient;
import com.TransferApp.MoneyTransfer.service.FavoriteRecipientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@Tag(name = "Favorite Recipients", description = "Endpoints for managing favorite recipients")
public class FavoriteRecipientController {

    @Autowired
    private FavoriteRecipientService favoriteRecipientService;
    @Operation(summary = "adding recipientId as a favorite at customer with id --> customerId")
    @ApiResponse(
            responseCode = "200",
            description = "favorite is added",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = FavoriteRecipient.class)
            )
    )
    @PostMapping("/{customerId}/{accountNumber}")
    public FavoriteRecipient addFavoriteRecipient(
            @PathVariable long customerId,
            @PathVariable String accountNumber) {

        return favoriteRecipientService.addFavoriteRecipient(customerId, accountNumber);
    }

    @Operation( summary = "getting all favorite recipients of customer with id --> customerId")
    @ApiResponse(

            responseCode = "200",
            description = "favorite is added",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = FavoriteRecipient.class)
            )
    )
    @GetMapping("/{customerId}")
    @Transactional
    public List<FavoriteRecipient> getFavoriteRecipients(@PathVariable long customerId) {
        return favoriteRecipientService.getFavoriteRecipients(customerId);
    }

      @Operation( summary = "removing favorite recipient with id --> id from customer with id --> customerId")
    @ApiResponse(
            responseCode = "200",
            description = "favorite is removed",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = FavoriteRecipient.class)
            )
    )
      @Transactional
    @DeleteMapping("/{customerId}/{reciepientId}")
    public void removeFavoriteRecipient(
            @PathVariable long customerId,
            @PathVariable long reciepientId) {

        favoriteRecipientService.removeFavoriteRecipient(customerId, reciepientId);
    }
}
