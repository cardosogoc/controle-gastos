package com.portifolio.controlegastos.business.dto.lancamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LancamentoRequest(

        @NotBlank
        @Size(max = 255)
        String descricao,

        @NotNull
        @Positive
        BigDecimal valor,

        @NotNull
        LocalDate data,

        @NotNull
        Long categoriaId

) {
}