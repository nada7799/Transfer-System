package com.TransferApp.MoneyTransfer.controllerTest;

import com.TransferApp.MoneyTransfer.controller.FavoriteRecipientController;
import com.TransferApp.MoneyTransfer.model.FavoriteRecipient;
import com.TransferApp.MoneyTransfer.service.FavoriteRecipientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FavoriteRecipientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FavoriteRecipientService favoriteRecipientService;

    @InjectMocks
    private FavoriteRecipientController favoriteRecipientController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(favoriteRecipientController).build();
    }

    @Test
    public void testAddFavoriteRecipient() throws Exception {
        FavoriteRecipient favoriteRecipient = new FavoriteRecipient();
        favoriteRecipient.setId(1L);

        when(favoriteRecipientService.addFavoriteRecipient(anyLong(), anyString()))
                .thenReturn(favoriteRecipient);

        mockMvc.perform(post("/api/favorites/{customerId}/{recipientId}", 1L, 2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(favoriteRecipientService, times(1)).addFavoriteRecipient(anyLong(), anyString());
    }

    @Test
    public void testGetFavoriteRecipients() throws Exception {
        FavoriteRecipient favoriteRecipient = new FavoriteRecipient();
        favoriteRecipient.setId(1L);

        when(favoriteRecipientService.getFavoriteRecipients(anyLong()))
                .thenReturn(Collections.singletonList(favoriteRecipient));

        mockMvc.perform(get("/api/favorites/{customerId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));

        verify(favoriteRecipientService, times(1)).getFavoriteRecipients(anyLong());
    }

    @Test
    public void testRemoveFavoriteRecipient() throws Exception {
        doNothing().when(favoriteRecipientService).removeFavoriteRecipient(anyLong(), anyLong());

        mockMvc.perform(delete("/api/favorites/{id}/{customerId}", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(favoriteRecipientService, times(1)).removeFavoriteRecipient(anyLong(), anyLong());
    }
}