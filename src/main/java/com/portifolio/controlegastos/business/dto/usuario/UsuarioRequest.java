package com.portifolio.controlegastos.business.dto.usuario;

import jakarta.validation.constraints.Size;

public record UsuarioRequest(

        @Size(max = 100)
        String nome,

        @Size(max = 20)
        String telefone
) {
}