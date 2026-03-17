package com.finguard.finguard.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String cardId;

    private double amount;

    private String merchant;

    private String location;

    private int riskScore;

    @Enumerated(EnumType.STRING)
    private TransactionState state;

    private LocalDateTime timestamp;

    // Automatically set values before saving
    @PrePersist
    public void prePersist() {
        this.timestamp = LocalDateTime.now();

        // Default values
        if (this.state == null) {
            this.state = TransactionState.PENDING;
        }

        this.riskScore = 0;
    }
}