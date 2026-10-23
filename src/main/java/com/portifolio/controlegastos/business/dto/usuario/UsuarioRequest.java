package com.portifolio.controlegastos.business.dto.usuario;

import jakarta.validation.constraints.Size;

public record UsuarioRequest(
        String nome,
        String email,

        @Size(min = 8)
        String senha,
        String telefone
) {
}
