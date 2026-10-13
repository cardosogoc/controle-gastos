package com.portifolio.controlegastos.business.mapper;

import com.portifolio.controlegastos.business.dto.categoria.CategoriaRequest;
import com.portifolio.controlegastos.business.dto.categoria.CategoriaResponse;
import com.portifolio.controlegastos.infrastructure.entity.Categoria;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    Categoria paraCategoriaEntity(CategoriaRequest dto);

    CategoriaResponse paraCategoriaDTO(Categoria entity);

    List<CategoriaResponse> paraListaCategoriasDTO(List<Categoria> listaEntity);
 }
