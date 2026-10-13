package com.portifolio.controlegastos.infrastructure.repository;

import com.portifolio.controlegastos.infrastructure.entity.Categoria;
import com.portifolio.controlegastos.infrastructure.enums.TipoEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {


    boolean existsByNomeAndTipoEnumAndUsuarioEmail(
            String nome,
            TipoEnum tipoEnum,
            String email
    );

    List<Categoria> findByUsuarioEmail(String email);
}
