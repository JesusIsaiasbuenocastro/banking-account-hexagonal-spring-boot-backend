package com.portafolio.bankingtransactions.infrastructure.web.controller;

import com.portafolio.bankingtransactions.application.dto.CreateAccountCommand;
import com.portafolio.bankingtransactions.application.dto.DepositMoneyCommand;
import com.portafolio.bankingtransactions.application.dto.WithDrawMoneyCommand;
import com.portafolio.bankingtransactions.application.port.CreateAccountUseCase;
import com.portafolio.bankingtransactions.application.port.DepositMoneyUseCase;
import com.portafolio.bankingtransactions.application.port.GetAccountDetailsUseCase;
import com.portafolio.bankingtransactions.application.port.WithdrawMoneyUseCase;
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
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cuenta creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Error inesperado",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody CreateAccountRequest request){
        var command = new CreateAccountCommand(request.customerId(), request.initialBalance());
        var dto = createAccountUseCase.createAccount(command);
        return ResponseEntity.status(201).body(AccountResponse.fromDto(dto));
    }

    @Operation(summary = "Depositar dinero", description = "Realiza un depósito sobre la cuenta indicada")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Depósito realizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Error inesperado",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping("/{id}/deposit")
    public ResponseEntity<AccountResponse> deposit(@PathVariable String id,@Valid @RequestBody DepositRequest request){
        var command = new DepositMoneyCommand(id, request.amount());
        var dto = depositMoneyUseCase.deposit(command);
        return ResponseEntity.ok(AccountResponse.fromDto(dto));
    }

    @Operation(summary = "Retirar dinero", description = "Realiza un retiro sobre la cuenta indicada, validando que haya fondos suficientes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retiro realizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o saldo insuficiente",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Error inesperado",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<AccountResponse> withdraw(@PathVariable String id,@Valid @RequestBody  WithdrawRequest request){
        var command = new WithDrawMoneyCommand(id, request.amount());
        var dto = withdrawMoneyUseCase.withdraw(command);
        return ResponseEntity.ok(AccountResponse.fromDto(dto));
    }

    @Operation(summary = "Obtener una cuenta", description = "Devuelve el detalle de una cuenta junto con su historial de transacciones")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuenta encontrada"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Error inesperado",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getById(@PathVariable String id){
        var dto = getAccountDetailsUseCase.getById(id);
        return ResponseEntity.ok(AccountResponse.fromDto(dto));
    }


}
