package com.portifolio.controlegastos.infrastructure.repository;

import com.portifolio.controlegastos.infrastructure.entity.Lancamento;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    boolean existsByCategoriaId(Long id);

    List<Lancamento> findByUsuarioEmail(String email);

    Optional<Lancamento> findByIdAndUsuarioEmail(Long id, String email);

    @EntityGraph(attributePaths = "categoria")
    List<Lancamento> findByUsuarioEmailAndDataBetweenOrderByDataDesc(
            String email, LocalDate inicio, LocalDate fim);
}
