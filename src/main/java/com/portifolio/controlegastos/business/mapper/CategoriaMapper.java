package com.portifolio.controlegastos.business.mapper;

import com.portifolio.controlegastos.business.dto.categoria.CategoriaRequest;
import com.portifolio.controlegastos.business.dto.categoria.CategoriaResponse;
import com.portifolio.controlegastos.infrastructure.entity.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Categoria paraCategoriaEntity(CategoriaRequest dto);

    CategoriaResponse paraCategoriaDTO(Categoria entity);

    List<CategoriaResponse> paraListaCategoriasDTO(List<Categoria> listaEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    void updateCategoria(CategoriaRequest dto, @MappingTarget Categoria entity);
}
