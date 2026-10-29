package com.portifolio.controlegastos.business.service;

import com.portifolio.controlegastos.business.dto.resumo.ResumoMensalResponse;
import com.portifolio.controlegastos.infrastructure.enums.TipoEnum;
import com.portifolio.controlegastos.infrastructure.repository.LancamentoRepository;
import com.portifolio.controlegastos.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;

@Service
@RequiredArgsConstructor
public class ResumoService {

    private final LancamentoRepository lancamentoRepository;
    private final JwtUtil jwtUtil;

    public ResumoMensalResponse resumoMensal(String token, int ano, int mes) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        YearMonth periodo = YearMonth.of(ano, mes);

        BigDecimal receitas = BigDecimal.ZERO;
        BigDecimal despesas = BigDecimal.ZERO;

        for (Object[] linha : lancamentoRepository.somarPorTipo(
                email, periodo.atDay(1), periodo.atEndOfMonth())) {
            TipoEnum tipo = (TipoEnum) linha[0];
            BigDecimal total = (BigDecimal) linha[1];
            if (tipo == TipoEnum.RECEITA) receitas = total;
            else despesas = total;
        }

        return new ResumoMensalResponse(receitas, despesas, receitas.subtract(despesas));
    }
}