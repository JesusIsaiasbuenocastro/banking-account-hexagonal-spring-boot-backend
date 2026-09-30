package com.portafolio.bankingtransactions.infrastructure.web.controller;

import com.portafolio.bankingtransactions.application.dto.CreateAccountCommand;
import com.portafolio.bankingtransactions.application.dto.DepositMoneyCommand;
import com.portafolio.bankingtransactions.application.dto.WithDrawMoneyCommand;
import com.portafolio.bankingtransactions.application.port.CreateAccountUseCase;
import com.portafolio.bankingtransactions.application.port.DepositMoneyUseCase;
import com.portafolio.bankingtransactions.application.port.GetAccountDetailsUseCase;
import com.portafolio.bankingtransactions.application.port.WithdrawMoneyUseCase;
import com.portafolio.bankingtransactions.infrastructure.web.annotations.ApiCommonErrorResponses;
import com.portafolio.bankingtransactions.infrastructure.web.annotations.ApiValidationErrorResponses;
import com.portafolio.bankingtransactions.infrastructure.web.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Operaciones bancarias", description = "Operaciones sobre cuentas bancarias: creación, consulta, depósitos y retiros")
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

    @Operation(summary =  "Crear una cuenta", description = "Crea una nueva cuenta bancaria con un saldo inicial")

            @ApiResponse(responseCode = "201", description = "Cuenta creada correctamente")
    @ApiValidationErrorResponses
    @PostMapping
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody CreateAccountRequest request){
        var command = new CreateAccountCommand(request.customerId(), request.initialBalance());
        var dto = createAccountUseCase.createAccount(command);
        return ResponseEntity.status(201).body(AccountResponse.fromDto(dto));
    }

    @Operation(summary = "Depositar dinero", description = "Realiza un depósito sobre la cuenta indicada")
    @ApiResponse(responseCode = "200", description = "Depósito realizado correctamente")


    @PostMapping("/{id}/deposit")
    @ApiCommonErrorResponses
    public ResponseEntity<AccountResponse> deposit(@PathVariable String id,@Valid @RequestBody DepositRequest request){
        var command = new DepositMoneyCommand(id, request.amount());
        var dto = depositMoneyUseCase.deposit(command);
        return ResponseEntity.ok(AccountResponse.fromDto(dto));
    }

    @Operation(summary = "Retirar dinero", description = "Realiza un retiro sobre la cuenta indicada, validando que haya fondos suficientes")
    @ApiResponse(responseCode = "200", description = "Retiro realizado correctamente")
    @ApiCommonErrorResponses
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<AccountResponse> withdraw(@PathVariable String id,@Valid @RequestBody  WithdrawRequest request){
        var command = new WithDrawMoneyCommand(id, request.amount());
        var dto = withdrawMoneyUseCase.withdraw(command);
        return ResponseEntity.ok(AccountResponse.fromDto(dto));
    }

    @Operation(summary = "Obtener una cuenta", description = "Devuelve el detalle de una cuenta junto con su historial de transacciones")
    @ApiResponse(responseCode = "200", description = "Cuenta encontrada")
    @ApiValidationErrorResponses
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getById(@PathVariable String id){
        var dto = getAccountDetailsUseCase.getById(id);
        return ResponseEntity.ok(AccountResponse.fromDto(dto));
    }


}
