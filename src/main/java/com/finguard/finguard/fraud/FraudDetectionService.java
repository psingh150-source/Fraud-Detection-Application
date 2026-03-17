package com.finguard.finguard.fraud;

import com.finguard.finguard.model.Transaction;
import com.finguard.finguard.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FraudDetectionService {

    private final TransactionRepository transactionRepository;

    public FraudDetectionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public int calculateRiskScore(Transaction transaction) {

        int score = 0;

        List<Transaction> history =
                transactionRepository.findByCardId(transaction.getCardId());

        // 🚨 Rule 1: High amount
        if (transaction.getAmount() > 20000) {
            score += 30;
        }

        // 🚨 Rule 2: Too many transactions (velocity fraud)
        if (history.size() > 5) {
            score += 40;
        }

        // 🚨 Rule 3: Suspicious location
        if ("Unknown".equalsIgnoreCase(transaction.getLocation())) {
            score += 20;
        }

        // 🚨 Rule 4: Same merchant repeated (optional smart rule)
        long sameMerchantCount = history.stream()
                .filter(t -> t.getMerchant().equalsIgnoreCase(transaction.getMerchant()))
                .count();

        if (sameMerchantCount > 3) {
            score += 10;
        }

        return score;
    }
}