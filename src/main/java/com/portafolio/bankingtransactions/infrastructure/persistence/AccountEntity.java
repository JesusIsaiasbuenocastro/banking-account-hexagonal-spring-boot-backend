package com.portafolio.bankingtransactions.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
@Setter
@Getter
public class AccountEntity {

    @Id
    private String id;
    @Column(nullable = false)
    private String customerId;
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionEntity> transactions = new ArrayList<>();

    public void addTransaction(TransactionEntity transactionEntity){
        transactionEntity.setAccount(this);
        this.transactions.add(transactionEntity);
    }

}
