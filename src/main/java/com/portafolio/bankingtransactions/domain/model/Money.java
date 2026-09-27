package com.portafolio.bankingtransactions.domain.model;

import java.math.BigDecimal;

public class Money {

    private final BigDecimal amount;

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public static Money of(double value){
        return new Money(BigDecimal.valueOf(value));
    }

    public static Money from(BigDecimal value){
        return new Money(value);
    }

    //deposit
    public Money add(Money money){
        return new Money(this.amount.add(money.amount));
    }
    //withdraw
    public Money substract(Money money){
        return new Money(this.amount.subtract(money.amount));
    }

    public boolean isNegative(){
        return this.amount.compareTo(BigDecimal.ZERO) < 0;
    }
    public BigDecimal getAmount(){
        return this.amount;
    }

}
