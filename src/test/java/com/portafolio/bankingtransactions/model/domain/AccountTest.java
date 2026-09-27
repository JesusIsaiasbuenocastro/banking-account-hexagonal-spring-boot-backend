package com.portafolio.bankingtransactions.model.domain;

import com.portafolio.bankingtransactions.domain.exception.InsufficientBalanceException;
import com.portafolio.bankingtransactions.domain.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    //AAA

    //Arrange  - inicializacion
    //Act -- Realizar accion
    //Afirm -- Afirmacion

    @Test
    void should_deposit_money_correct(){
        //arrange
        Account account = new Account(AccountId.newId(),"23234234", Money.of(100));

        //act
        account.deposit(Money.of(50));

        //afirm
        assertEquals(Money.of(150).getAmount(), account.getBalance().getAmount());
        assertFalse(account.getTransactions().isEmpty());
        assertEquals(TransactionType.DEPOSIT, account.getTransactions().getFirst().getTransactionType());
    }

    @Test
    void should_not_allow_withdraw_when_insufficient_balance(){
        //arrange
        Account account = new Account(AccountId.newId(),"23234234", Money.of(100));

        //act
        InsufficientBalanceException ex = assertThrows(
          InsufficientBalanceException.class, ()-> account.withdraw(Money.of(150))
        );

        //assert
        assertNotNull(ex.getMessage());
        assertEquals(Money.of(100).getAmount(), account.getBalance().getAmount());
        assertTrue(account.getTransactions().isEmpty());
    }
}
