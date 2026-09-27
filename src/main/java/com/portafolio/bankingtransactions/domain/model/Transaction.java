package com.portafolio.bankingtransactions.domain.model;

import java.time.Instant;
import java.util.UUID;

public class Transaction {

    private  final String id;
    private final TransactionType transactionType;
    private final Money amount;
    private final Instant createAt;

    public Transaction(TransactionType transactionType, Money money) {
        this.id = UUID.randomUUID().toString();
        this.transactionType = transactionType;
        this.amount = money;
        this.createAt = Instant.now();
    }

    public String getId() {
        return id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Money getAmount() {
        return amount;
    }

    public Instant getCreateAt() {
        return createAt;
    }
}
