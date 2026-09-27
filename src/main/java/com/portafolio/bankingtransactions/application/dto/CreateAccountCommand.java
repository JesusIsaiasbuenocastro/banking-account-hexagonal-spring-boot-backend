package com.portafolio.bankingtransactions.application.dto;

public record CreateAccountCommand(
        String customerId,
        double initialBalance
) {
}
