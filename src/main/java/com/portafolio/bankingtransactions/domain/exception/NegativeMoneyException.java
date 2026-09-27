package com.portafolio.bankingtransactions.domain.exception;

public class NegativeMoneyException extends RuntimeException{
    public NegativeMoneyException(String message){
        super(message);
    }
}
