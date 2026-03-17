package com.finguard.finguard.repository;

import com.finguard.finguard.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository
        extends JpaRepository<Transaction, String> {

    // Find all transactions by cardId (used for fraud detection)
    List<Transaction> findByCardId(String cardId);
}