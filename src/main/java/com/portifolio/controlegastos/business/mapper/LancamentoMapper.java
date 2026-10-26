package com.portifolio.controlegastos.business.mapper;

import com.portifolio.controlegastos.business.dto.lancamento.LancamentoRequest;
import com.portifolio.controlegastos.business.dto.lancamento.LancamentoResponse;
import com.portifolio.controlegastos.infrastructure.entity.Lancamento;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LancamentoMapper {

    Lancamento paraLancamentoEntity(LancamentoRequest dto);

    LancamentoResponse paraLancamentoDTO(Lancamento entity);
}
