package com.portifolio.controlegastos.controller.docs;

import com.portifolio.controlegastos.business.dto.resumo.ResumoMensalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Resumo", description = "Resumos financeiros do usuário autenticado")
@SecurityRequirement(name = "bearerAuth")
public interface ResumoControllerDocs {

    @Operation(
            summary = "Resumo mensal",
            description = "Retorna o resumo financeiro do usuário autenticado em um mês e ano específicos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resumo gerado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Mês ou ano inválido"),
            @ApiResponse(responseCode = "401", description = "Token inválido ou ausente")
    })
    ResponseEntity<ResumoMensalResponse> resumoMensal(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token,
            @Parameter(description = "Ano do resumo", example = "2026") @RequestParam int ano,
            @Parameter(description = "Mês do resumo (1 a 12)", example = "10") @RequestParam int mes);
}