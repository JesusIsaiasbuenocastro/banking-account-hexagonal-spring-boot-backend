package com.portafolio.bankingtransactions.infrastructure.web.dto;

public record TransactionSummary(
        String id,
        String type,
        double amount
) {
}
