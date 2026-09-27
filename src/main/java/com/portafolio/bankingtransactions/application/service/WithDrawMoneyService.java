package com.portafolio.bankingtransactions.application.service;

import com.portafolio.bankingtransactions.application.dto.AccountDetailsDto;
import com.portafolio.bankingtransactions.application.dto.MapToAcountDatailsDto;
import com.portafolio.bankingtransactions.application.dto.WithDrawMoneyCommand;
import com.portafolio.bankingtransactions.application.port.WithdrawMoneyUseCase;
import com.portafolio.bankingtransactions.domain.exception.AccountNotFoundException;
import com.portafolio.bankingtransactions.domain.model.AccountId;
import com.portafolio.bankingtransactions.domain.model.Money;
import com.portafolio.bankingtransactions.domain.port.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class WithDrawMoneyService implements WithdrawMoneyUseCase {

    private final AccountRepository accountRepository;

    public WithDrawMoneyService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public AccountDetailsDto withdraw(WithDrawMoneyCommand command) {
        var accountId = new AccountId(command.accountId());

        var account = accountRepository.findById(accountId)
                .orElseThrow( () -> new AccountNotFoundException(command.accountId()));

        account.withdraw(Money.of(command.amount()));

        accountRepository.save(account);

        return MapToAcountDatailsDto.from(account);

    }
}
