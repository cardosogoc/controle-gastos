package com.portifolio.controlegastos.business.dto.lancamento;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LancamentoRequest(
        @NotBlank @Size(max = 255) String descricao,
        @NotNull @Positive @Digits(integer = 10, fraction = 2) BigDecimal valor,
        @NotNull LocalDate data,
        @NotNull Long categoriaId
) {
}