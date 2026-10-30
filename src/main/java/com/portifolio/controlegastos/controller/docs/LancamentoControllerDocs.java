package com.portifolio.controlegastos.controller.docs;

import com.portifolio.controlegastos.business.dto.lancamento.LancamentoRequest;
import com.portifolio.controlegastos.business.dto.lancamento.LancamentoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Lançamento", description = "Gerenciamento dos lançamentos financeiros do usuário autenticado")
@SecurityRequirement(name = "bearerAuth")
public interface LancamentoControllerDocs {

    @Operation(
            summary = "Criar lançamento",
            description = "Cadastra um novo lançamento para o usuário autenticado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lançamento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Token inválido ou ausente"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    ResponseEntity<LancamentoResponse> criarLancamento(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token,
            @Valid @RequestBody LancamentoRequest dto);


    @Operation(
            summary = "Listar lançamentos",
            description = "Retorna todos os lançamentos do usuário autenticado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lançamentos listados com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token inválido ou ausente")
    })
    ResponseEntity<List<LancamentoResponse>> listarLancamentoUsuario(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token);


    @Operation(
            summary = "Editar lançamento",
            description = "Atualiza os dados de um lançamento do usuário autenticado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lançamento atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Token inválido ou ausente"),
            @ApiResponse(responseCode = "404", description = "Lançamento não encontrado")
    })
    ResponseEntity<LancamentoResponse> editarLancamento(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token,
            @Parameter(description = "ID do lançamento", example = "1") @PathVariable Long id,
            @Valid @RequestBody LancamentoRequest dto);


    @Operation(
            summary = "Excluir lançamento",
            description = "Exclui um lançamento do usuário autenticado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Lançamento excluído com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token inválido ou ausente"),
            @ApiResponse(responseCode = "404", description = "Lançamento não encontrado")
    })
    ResponseEntity<Void> deletaLancamento(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token,
            @Parameter(description = "ID do lançamento", example = "1") @PathVariable Long id);


    @Operation(
            summary = "Listar lançamentos por mês",
            description = "Retorna os lançamentos do usuário autenticado em um mês e ano específicos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lançamentos listados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Mês ou ano inválido"),
            @ApiResponse(responseCode = "401", description = "Token inválido ou ausente")
    })
    ResponseEntity<List<LancamentoResponse>> listarPorMes(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token,
            @Parameter(description = "Ano do lançamento", example = "2026") @RequestParam int ano,
            @Parameter(description = "Mês do lançamento (1 a 12)", example = "10")
            @RequestParam @Min(1) @Max(12) int mes);
}