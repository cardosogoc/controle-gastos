
package com.portifolio.controlegastos.controller.docs;

import com.portifolio.controlegastos.business.dto.usuario.LoginRequest;
import com.portifolio.controlegastos.business.dto.usuario.UsuarioRequest;
import com.portifolio.controlegastos.business.dto.usuario.UsuarioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "Usuário", description = "Cadastro e gerenciamento de usuários")
public interface UsuarioControllerDocs {

    @Operation(
            summary = "Cadastrar usuário",
            description = "Realiza o cadastro de um novo usuário."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "E-mail já cadastrado")
    })
    ResponseEntity<UsuarioResponse> criarUsuario(@RequestBody UsuarioRequest dto);


    @Operation(
            summary = "Autenticar usuário",
            description = "Valida as credenciais e retorna um token JWT."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    ResponseEntity<String> login(@RequestBody LoginRequest dto);


    @Operation(
            summary = "Editar perfil",
            description = "Atualiza os dados do usuário autenticado.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil atualizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token inválido ou ausente"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<UsuarioResponse> editarPerfil(
            @RequestBody UsuarioRequest dto,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token);


    @Operation(
            summary = "Consultar perfil",
            description = "Retorna os dados do usuário autenticado.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil consultado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token inválido ou ausente"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<UsuarioResponse> acessarPerfil(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token);


    @Operation(
            summary = "Excluir conta",
            description = "Exclui permanentemente a conta do usuário autenticado.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Conta excluída com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token inválido ou ausente"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<Void> deletaUsuario(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token);
}
