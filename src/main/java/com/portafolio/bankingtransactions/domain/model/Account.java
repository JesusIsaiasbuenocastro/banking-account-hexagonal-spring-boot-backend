package com.portafolio.bankingtransactions.domain.model;

import com.portafolio.bankingtransactions.domain.exception.InsufficientBalanceException;
import com.portafolio.bankingtransactions.domain.exception.NegativeMoneyException;

import java.math.BigDecimal;
import java.util.*;

public class Account {
    private final AccountId id;
    private final String customerId;
    private Money balance;
    private List<Transaction> transactions = new ArrayList<>();

    public Account(AccountId id, String customerId, Money initialBalance) {
        this.id = id;
        this.customerId = customerId;
        this.balance =initialBalance;
    }
    //deposit
    public void deposit(Money amount){
        Objects.requireNonNull(amount, "Deposit must not be null");
        if(amount.getAmount().compareTo(BigDecimal.ZERO) <= 0 ){
            throw new NegativeMoneyException("Deposit amount must be greter than zero");
        }
        this.balance = this.balance.add(amount);
        this.transactions.add(new Transaction(TransactionType.DEPOSIT,amount));
    }

    //retiro
    public void withdraw(Money amount){
        Objects.requireNonNull(amount, "WithDraw must not be null");
        if(amount.getAmount().compareTo(BigDecimal.ZERO) <= 0 ){
            throw new NegativeMoneyException("WithDraw amount must be greater than zero");
        }
        Money newBalance = this.balance.substract(amount);
        if(newBalance.isNegative()){
            throw new InsufficientBalanceException("Insuficient founds for withdraw");
        }
        this.balance = newBalance;
        this.transactions.add(new Transaction(TransactionType.WITHDRAW, amount));
    }

    public List<Transaction>  getTransactions(){
        //return Collections.unmodifiableList(this.transactions);
        return this.transactions;
    }

    public String getCustomerId() {
        return customerId;
    }

    public AccountId getId() {
        return id;
    }

    public Money getBalance() {
        return balance;
    }
}
