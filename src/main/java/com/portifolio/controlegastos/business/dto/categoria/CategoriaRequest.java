package com.portifolio.controlegastos.business.dto.categoria;

import com.portifolio.controlegastos.infrastructure.enums.TipoEnum;

public record CategoriaRequest(String nome, TipoEnum tipoEnum) {
}
