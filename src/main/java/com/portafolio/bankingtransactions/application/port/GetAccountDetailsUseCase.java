package com.portafolio.bankingtransactions.application.port;

import com.portafolio.bankingtransactions.application.dto.AccountDetailsDto;

public interface GetAccountDetailsUseCase {
    AccountDetailsDto getById(String accountId);
}
