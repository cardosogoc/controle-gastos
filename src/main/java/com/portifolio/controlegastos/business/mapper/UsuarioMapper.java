package com.portifolio.controlegastos.business.mapper;

import com.portifolio.controlegastos.business.dto.usuario.UsuarioRequest;
import com.portifolio.controlegastos.business.dto.usuario.UsuarioResponse;
import com.portifolio.controlegastos.infrastructure.entity.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario paraUsuarioEntity(UsuarioRequest dto);

    UsuarioResponse paraUsuarioDTO(Usuario entity);

}
