package com.portifolio.controlegastos.business.mapper;

import com.portifolio.controlegastos.business.dto.categoria.CategoriaRequest;
import com.portifolio.controlegastos.business.dto.categoria.CategoriaResponse;
import com.portifolio.controlegastos.infrastructure.entity.Categoria;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    Categoria paraCategoriaEntity(CategoriaRequest dto);

    CategoriaResponse paraCategoriaDTO(Categoria entity);
}
