package com.portafolio.bankingtransactions.application.dto;

public record DepositMoneyCommand(
        String accountId,
        double amount
) {
}
