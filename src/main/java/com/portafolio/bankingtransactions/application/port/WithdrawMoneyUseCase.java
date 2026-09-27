package com.portafolio.bankingtransactions.application.port;

import com.portafolio.bankingtransactions.application.dto.AccountDetailsDto;
import com.portafolio.bankingtransactions.application.dto.WithDrawMoneyCommand;

public interface WithdrawMoneyUseCase {
    AccountDetailsDto withdraw(WithDrawMoneyCommand command);
}
