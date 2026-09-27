package com.portafolio.bankingtransactions.application.dto;

import java.util.List;

public record AccountDetailsDto(
        String id,
        String customerId,
        double balance,
        List<TransactionDto> transactions
) {
}
