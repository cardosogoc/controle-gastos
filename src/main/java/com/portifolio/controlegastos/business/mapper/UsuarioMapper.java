package com.portifolio.controlegastos.business.mapper;

import com.portifolio.controlegastos.business.dto.usuario.UsuarioRequest;
import com.portifolio.controlegastos.business.dto.usuario.UsuarioResponse;
import com.portifolio.controlegastos.infrastructure.entity.Usuario;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario paraUsuarioEntity(UsuarioRequest dto);

    UsuarioResponse paraUsuarioDTO(Usuario entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void atualizarUsuario(UsuarioRequest dto, @MappingTarget Usuario usuario);

}
