package com.portafolio.bankingtransactions.application.service;

import com.portafolio.bankingtransactions.application.dto.AccountDetailsDto;
import com.portafolio.bankingtransactions.application.dto.MapToAcountDatailsDto;
import com.portafolio.bankingtransactions.application.port.GetAccountDetailsUseCase;
import com.portafolio.bankingtransactions.domain.exception.AccountNotFoundException;
import com.portafolio.bankingtransactions.domain.model.AccountId;
import com.portafolio.bankingtransactions.domain.port.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class GetAccountDetailsService implements GetAccountDetailsUseCase {

    private final AccountRepository accountRepository;

    public GetAccountDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public AccountDetailsDto getById(String accountId) {
        var id = new AccountId(accountId);
        var account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
        return MapToAcountDatailsDto.from(account);
    }
}
