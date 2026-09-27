package com.portafolio.bankingtransactions.application.dto;

public record WithDrawMoneyCommand(
        String accountId,
        double amount
) {
}
