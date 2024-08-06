package com.TransferApp.MoneyTransfer.serviceTest;

import com.TransferApp.MoneyTransfer.model.Customer;
import com.TransferApp.MoneyTransfer.model.FavoriteRecipient;
import com.TransferApp.MoneyTransfer.reporsitory.CustomerRepository;
import com.TransferApp.MoneyTransfer.reporsitory.FavoriteRecipientRepository;
import com.TransferApp.MoneyTransfer.service.FavoriteRecipientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FavoriteRecipientServiceTest {

    @InjectMocks
    private FavoriteRecipientService favoriteRecipientService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private FavoriteRecipientRepository favoriteRecipientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddFavoriteRecipient_Success() {
        // Arrange
        long customerId = 1L;
        String accountNumber = "1234567890";;

        Customer customer = Customer.builder().id(customerId).build();
        Customer recipient = Customer.builder().id(2L).build();

        FavoriteRecipient favoriteRecipient = FavoriteRecipient.builder()
                .customer(customer)
                .recipient(recipient)
                .build();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.findCustomerByAccountNumber(accountNumber)).thenReturn(Optional.of(recipient));
        when(favoriteRecipientRepository.save(any(FavoriteRecipient.class))).thenReturn(favoriteRecipient);

        // Act
        FavoriteRecipient result = favoriteRecipientService.addFavoriteRecipient(customerId, accountNumber);

        // Assert
        assertNotNull(result);
        assertEquals(customer, result.getCustomer());
        assertEquals(recipient, result.getRecipient());
    }

    @Test
    void testAddFavoriteRecipient_CustomerNotFound() {
        // Arrange
        long customerId = 1L;
        String accountNumber = "1234567890";

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
        when(customerRepository.findCustomerByAccountNumber(accountNumber)).thenReturn(Optional.of(new Customer()));

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            favoriteRecipientService.addFavoriteRecipient(customerId, accountNumber);
        });
        assertEquals("Customer not found", thrown.getMessage());
    }

    @Test
    void testGetFavoriteRecipients_Success() {
        // Arrange
        long customerId = 1L;
        List<FavoriteRecipient> favoriteRecipients = Arrays.asList(
                FavoriteRecipient.builder().customer(Customer.builder().id(customerId).build()).build()
        );

        when(favoriteRecipientRepository.findByCustomerId(customerId)).thenReturn(favoriteRecipients);

        // Act
        List<FavoriteRecipient> result = favoriteRecipientService.getFavoriteRecipients(customerId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testRemoveFavoriteRecipient_Success() {
        // Arrange
        long id = 1L;
        long customerId = 2L;

        // Act
        favoriteRecipientService.removeFavoriteRecipient(id, customerId);

        // Assert
        verify(favoriteRecipientRepository, times(1)).deleteByCustomerIdAndRecipientId(id, customerId);
    }
}