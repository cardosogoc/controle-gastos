package com.portifolio.controlegastos.business.dto.resumo;

import java.math.BigDecimal;

public record ResumoMensalResponse(
        BigDecimal totalReceitas,
        BigDecimal totalDespesas,
        BigDecimal saldo
) {
}
