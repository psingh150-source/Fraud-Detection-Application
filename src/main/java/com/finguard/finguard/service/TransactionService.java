package com.finguard.finguard.service;

import com.finguard.finguard.model.Transaction;
import com.finguard.finguard.model.TransactionState;
import com.finguard.finguard.repository.TransactionRepository;
import com.finguard.finguard.fraud.FraudDetectionService;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final FraudDetectionService fraudDetectionService;

    public TransactionService(TransactionRepository transactionRepository,
                              FraudDetectionService fraudDetectionService) {
        this.transactionRepository = transactionRepository;
        this.fraudDetectionService = fraudDetectionService;
    }

    public Transaction processTransaction(Transaction transaction) {

        int riskScore = fraudDetectionService.calculateRiskScore(transaction);

        transaction.setRiskScore(riskScore);

        if (riskScore > 50) {
            transaction.setState(TransactionState.FLAGGED);
        } else {
            transaction.setState(TransactionState.CLEARED);
        }

        return transactionRepository.save(transaction);
    }
}