package com.portifolio.controlegastos.business.dto.categoria;

import com.portifolio.controlegastos.business.dto.usuario.UsuarioResponse;
import com.portifolio.controlegastos.infrastructure.enums.TipoEnum;

public record CategoriaResponse(Long id,String nome, TipoEnum tipoEnum, UsuarioResponse usuario) {


}
