package com.portafolio.bankingtransactions.application.port;

import com.portafolio.bankingtransactions.application.dto.AccountDetailsDto;
import com.portafolio.bankingtransactions.application.dto.DepositMoneyCommand;

public interface DepositMoneyUseCase {
    AccountDetailsDto deposit(DepositMoneyCommand command);
}
