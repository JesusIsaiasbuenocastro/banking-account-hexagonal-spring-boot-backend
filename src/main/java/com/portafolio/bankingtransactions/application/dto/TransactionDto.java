package com.portafolio.bankingtransactions.application.dto;

public record TransactionDto(
        String id,
        String type,
        double amount
) {
}
