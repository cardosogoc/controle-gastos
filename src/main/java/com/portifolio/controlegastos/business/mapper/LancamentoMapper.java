package com.portifolio.controlegastos.business.mapper;

import com.portifolio.controlegastos.business.dto.lancamento.LancamentoRequest;
import com.portifolio.controlegastos.business.dto.lancamento.LancamentoResponse;
import com.portifolio.controlegastos.infrastructure.entity.Lancamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LancamentoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    Lancamento paraLancamentoEntity(LancamentoRequest dto);

    @Mapping(source = "categoria.id", target = "categoriaId")
    @Mapping(source = "categoria.nome", target = "categoriaNome")
    LancamentoResponse paraLancamentoDTO(Lancamento entity);

    List<LancamentoResponse> listaLancamentoDTO(List<Lancamento> listaEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    void updateLancamento(LancamentoRequest dto, @MappingTarget Lancamento entity);
}
