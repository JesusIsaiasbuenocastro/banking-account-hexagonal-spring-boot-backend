package com.portafolio.bankingtransactions.application.dto;

import com.portafolio.bankingtransactions.domain.model.Account;
import com.portafolio.bankingtransactions.domain.model.Transaction;

import java.util.List;

public class MapToAcountDatailsDto {

    public static  AccountDetailsDto from (Account account){
        List<TransactionDto> transactionDtoList = account.getTransactions().stream()
                .map(MapToAcountDatailsDto::mapTransaction)
                .toList();
        return  new AccountDetailsDto(
                account.getId().value(),
                account.getCustomerId(),
                account.getBalance().getAmount().doubleValue(),
                transactionDtoList
        );
    }

    private  static TransactionDto mapTransaction(Transaction transaction){
        return new TransactionDto(
                transaction.getId(),
                transaction.getTransactionType().name(),
                transaction.getAmount().getAmount().doubleValue()
        );
    }


}
