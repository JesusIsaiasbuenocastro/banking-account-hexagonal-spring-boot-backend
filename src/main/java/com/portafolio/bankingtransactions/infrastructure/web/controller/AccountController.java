package com.portafolio.bankingtransactions.infrastructure.web.controller;

import com.portafolio.bankingtransactions.application.dto.CreateAccountCommand;
import com.portafolio.bankingtransactions.application.dto.DepositMoneyCommand;
import com.portafolio.bankingtransactions.application.dto.WithDrawMoneyCommand;
import com.portafolio.bankingtransactions.application.port.CreateAccountUseCase;
import com.portafolio.bankingtransactions.application.port.DepositMoneyUseCase;
import com.portafolio.bankingtransactions.application.port.GetAccountDetailsUseCase;
import com.portafolio.bankingtransactions.application.port.WithdrawMoneyUseCase;
import com.portafolio.bankingtransactions.infrastructure.web.dto.AccountResponse;
import com.portafolio.bankingtransactions.infrastructure.web.dto.CreateAccountRequest;
import com.portafolio.bankingtransactions.infrastructure.web.dto.DepositRequest;
import com.portafolio.bankingtransactions.infrastructure.web.dto.WithdrawRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final CreateAccountUseCase createAccountUseCase;
    private final DepositMoneyUseCase depositMoneyUseCase;
    private final WithdrawMoneyUseCase withdrawMoneyUseCase;
    private final GetAccountDetailsUseCase getAccountDetailsUseCase;

    public AccountController(CreateAccountUseCase createAccountUseCase, DepositMoneyUseCase depositMoneyUseCase, WithdrawMoneyUseCase withdrawMoneyUseCase, GetAccountDetailsUseCase getAccountDetailsUseCase) {
        this.createAccountUseCase = createAccountUseCase;
        this.depositMoneyUseCase = depositMoneyUseCase;
        this.withdrawMoneyUseCase = withdrawMoneyUseCase;
        this.getAccountDetailsUseCase = getAccountDetailsUseCase;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody CreateAccountRequest request){
        var command = new CreateAccountCommand(request.customerId(), request.initialBalance());
        var dto = createAccountUseCase.createAccount(command);
        return ResponseEntity.status(201).body(AccountResponse.fromDto(dto));
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<AccountResponse> deposit(@PathVariable String id,@Valid @RequestBody DepositRequest request){
        var command = new DepositMoneyCommand(id, request.amount());
        var dto = depositMoneyUseCase.deposit(command);
        return ResponseEntity.ok(AccountResponse.fromDto(dto));
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<AccountResponse> withdraw(@PathVariable String id,@Valid @RequestBody  WithdrawRequest request){
        var command = new WithDrawMoneyCommand(id, request.amount());
        var dto = withdrawMoneyUseCase.withdraw(command);
        return ResponseEntity.ok(AccountResponse.fromDto(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getById(@PathVariable String id){
        var dto = getAccountDetailsUseCase.getById(id);
        return ResponseEntity.ok(AccountResponse.fromDto(dto));
    }


}
