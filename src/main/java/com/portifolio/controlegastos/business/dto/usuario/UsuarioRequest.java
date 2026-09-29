package com.portifolio.controlegastos.business.dto.usuario;

public record UsuarioRequest(
        String nome,
        String email,
        String senha,
        String telefone
) {
}
