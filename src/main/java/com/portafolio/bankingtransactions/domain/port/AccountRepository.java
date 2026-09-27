package com.portafolio.bankingtransactions.domain.port;

import com.portafolio.bankingtransactions.domain.model.Account;
import com.portafolio.bankingtransactions.domain.model.AccountId;

import java.util.Optional;

public interface AccountRepository {

    Account save(Account account);
    Optional<Account> findById(AccountId accountId);
}
