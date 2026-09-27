package com.portafolio.bankingtransactions.infrastructure.web.dto;

import jakarta.validation.constraints.Positive;

public record DepositRequest(
        @Positive  double amount
) {
}
