package com.portifolio.controlegastos.business.dto.usuario;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        String telefone
) {
}
