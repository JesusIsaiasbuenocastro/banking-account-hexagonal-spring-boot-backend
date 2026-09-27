package com.portafolio.bankingtransactions.application.service;

import com.portafolio.bankingtransactions.application.dto.AccountDetailsDto;
import com.portafolio.bankingtransactions.application.dto.CreateAccountCommand;
import com.portafolio.bankingtransactions.application.dto.MapToAcountDatailsDto;
import com.portafolio.bankingtransactions.application.port.CreateAccountUseCase;
import com.portafolio.bankingtransactions.domain.model.Account;
import com.portafolio.bankingtransactions.domain.model.AccountId;
import com.portafolio.bankingtransactions.domain.model.Money;
import com.portafolio.bankingtransactions.domain.port.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CreateAccountService implements CreateAccountUseCase {

    private final AccountRepository accountRepository;

    public CreateAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public AccountDetailsDto createAccount(CreateAccountCommand command) {
        Account account = new Account(
            AccountId.newId(),
            command.customerId(),
            Money.of(command.initialBalance())
        );
       Account saved = accountRepository.save(account);
       return MapToAcountDatailsDto.from(saved);
    }
}
