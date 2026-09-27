package com.portafolio.bankingtransactions.application.port;

import com.portafolio.bankingtransactions.application.dto.AccountDetailsDto;
import com.portafolio.bankingtransactions.application.dto.CreateAccountCommand;

public interface CreateAccountUseCase {
    AccountDetailsDto createAccount(CreateAccountCommand command);
}
