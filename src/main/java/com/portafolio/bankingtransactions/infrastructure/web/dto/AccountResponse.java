package com.portafolio.bankingtransactions.infrastructure.web.dto;

import com.portafolio.bankingtransactions.application.dto.AccountDetailsDto;

import java.util.List;

public record AccountResponse(
        String id,
        String customerId,
        double balance,
        List<TransactionSummary> transactions
) {
    public static AccountResponse fromDto(AccountDetailsDto dto){
        List<TransactionSummary> transactionSummary = dto.transactions().stream()
                .map(t -> new TransactionSummary(
                        t.id(),
                        t.type(),
                        t.amount()
                )).toList();

        return  new AccountResponse(dto.id(), dto.customerId(),dto.balance(), transactionSummary);
    }
}
