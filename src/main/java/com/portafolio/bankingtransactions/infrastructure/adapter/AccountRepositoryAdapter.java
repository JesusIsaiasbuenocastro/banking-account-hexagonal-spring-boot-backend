package com.portafolio.bankingtransactions.infrastructure.adapter;

import com.portafolio.bankingtransactions.domain.model.Account;
import com.portafolio.bankingtransactions.domain.model.AccountId;
import com.portafolio.bankingtransactions.domain.port.AccountRepository;
import com.portafolio.bankingtransactions.infrastructure.mapper.AccountMapper;
import com.portafolio.bankingtransactions.infrastructure.persistence.AccountEntity;
import com.portafolio.bankingtransactions.infrastructure.repository.SpringDataAccountRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountRepositoryAdapter implements AccountRepository {

    private final SpringDataAccountRepository springDataAccountRepository;

    public AccountRepositoryAdapter(SpringDataAccountRepository springDataAccountRepository) {
        this.springDataAccountRepository = springDataAccountRepository;
    }

    @Override
    public Account save(Account account) {
        AccountEntity accountEntity = AccountMapper.toEntity(account);
        AccountEntity saved = springDataAccountRepository.save(accountEntity);
        return AccountMapper.toDomain(saved);
    }

    @Override
    public Optional<Account> findById(AccountId accountId) {
        return springDataAccountRepository.findById(accountId.value()).map(
                AccountMapper::toDomain
        );
    }
}
