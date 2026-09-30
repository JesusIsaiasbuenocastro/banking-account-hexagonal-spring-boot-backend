package com.portafolio.bankingtransactions.infrastructure.web.annotations;

import com.portafolio.bankingtransactions.infrastructure.web.dto.ErrorMessage;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Agrupa los códigos de error para endpoints que no buscan una cuenta existente
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses({
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
        @ApiResponse(responseCode = "500", description = "Error inesperado",
                content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
})
public @interface ApiValidationErrorResponses {
}
