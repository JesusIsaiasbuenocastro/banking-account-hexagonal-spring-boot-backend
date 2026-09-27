package com.portafolio.bankingtransactions.infrastructure.mapper;

import com.portafolio.bankingtransactions.domain.model.*;
import com.portafolio.bankingtransactions.infrastructure.persistence.AccountEntity;
import com.portafolio.bankingtransactions.infrastructure.persistence.TransactionEntity;

public class AccountMapper {

    //Mapeo jpa a una entidad de dominio
    public static Account toDomain(AccountEntity entity){
        Account account = new Account(
                new AccountId(entity.getId()),
                entity.getCustomerId(),
                Money.from(entity.getBalance())
        );

        entity.getTransactions().forEach(
                txs-> {
                    Transaction tx = new Transaction(
                            TransactionType.valueOf(txs.getType()),
                            Money.from(txs.getAmount())
                    );
                    account.getTransactions().add(tx);        ;
                }
        );
        return account;
    }
    //Mapeo de una entidad de dominio a una entidad jpa
    public static AccountEntity toEntity(Account account){
        AccountEntity entity = new AccountEntity();
        entity.setId(account.getId().value());
        entity.setCustomerId(account.getCustomerId());
        entity.setBalance(account.getBalance().getAmount());

        entity.getTransactions().clear();

        for(Transaction t: account.getTransactions()){
            TransactionEntity te = new TransactionEntity();
            te.setId(t.getId());
            te.setType(t.getTransactionType().name());
            te.setAmount(t.getAmount().getAmount());
            entity.addTransaction(te);
        }
        return entity;
    }
}
