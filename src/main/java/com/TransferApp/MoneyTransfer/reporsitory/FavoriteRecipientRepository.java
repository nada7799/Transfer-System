package com.TransferApp.MoneyTransfer.reporsitory;

import com.TransferApp.MoneyTransfer.model.FavoriteRecipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRecipientRepository extends JpaRepository<FavoriteRecipient, Long> {
    List<FavoriteRecipient> findByCustomerId(long customerId);
    void deleteByCustomerIdAndRecipientId(long id, long customerId);
}
