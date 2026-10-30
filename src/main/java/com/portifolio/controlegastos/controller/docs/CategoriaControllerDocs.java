package com.portifolio.controlegastos.controller.docs;

import com.portifolio.controlegastos.business.dto.categoria.CategoriaRequest;
import com.portifolio.controlegastos.business.dto.categoria.CategoriaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Tag(name = "Categoria", description = "Gerenciamento das categorias do usuário autenticado")
@SecurityRequirement(name = "bearerAuth")
public interface CategoriaControllerDocs {

    @Operation(
            summary = "Criar categoria",
            description = "Cadastra uma nova categoria para o usuário autenticado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Token inválido ou ausente")
    })
    ResponseEntity<CategoriaResponse> criarCategoria(
            @RequestBody CategoriaRequest dto,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token);


    @Operation(
            summary = "Listar categorias",
            description = "Retorna todas as categorias do usuário autenticado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorias listadas com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token inválido ou ausente")
    })
    ResponseEntity<List<CategoriaResponse>> listarCategoriasUsuario(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token);


    @Operation(
            summary = "Excluir categoria",
            description = "Exclui uma categoria do usuário autenticado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria excluída com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token inválido ou ausente"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    ResponseEntity<Void> deletaCategoria(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token,
            @Parameter(description = "ID da categoria", example = "1") @PathVariable Long id);


    @Operation(
            summary = "Editar categoria",
            description = "Atualiza os dados de uma categoria do usuário autenticado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Token inválido ou ausente"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    ResponseEntity<CategoriaResponse> editaCategoria(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token,
            @Parameter(description = "ID da categoria", example = "1") @PathVariable Long id,
            @Valid @RequestBody CategoriaRequest dto);
}