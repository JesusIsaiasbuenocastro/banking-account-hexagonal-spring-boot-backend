package com.portafolio.bankingtransactions.application.service;

import com.portafolio.bankingtransactions.application.dto.AccountDetailsDto;
import com.portafolio.bankingtransactions.application.dto.DepositMoneyCommand;
import com.portafolio.bankingtransactions.application.dto.MapToAcountDatailsDto;
import com.portafolio.bankingtransactions.application.port.DepositMoneyUseCase;
import com.portafolio.bankingtransactions.domain.exception.AccountNotFoundException;
import com.portafolio.bankingtransactions.domain.model.AccountId;
import com.portafolio.bankingtransactions.domain.model.Money;
import com.portafolio.bankingtransactions.domain.port.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DepositMoneyService implements DepositMoneyUseCase {

    private final AccountRepository accountRepository;

    public DepositMoneyService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    @Transactional
    public AccountDetailsDto deposit(DepositMoneyCommand command) {
        var accountId = new AccountId(command.accountId());

        var account = accountRepository.findById(accountId)
                .orElseThrow( () -> new AccountNotFoundException(command.accountId()));

        account.deposit( Money.of(command.amount()));

        accountRepository.save(account);

        return MapToAcountDatailsDto.from(account);
    }
}
