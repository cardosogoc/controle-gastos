package com.portifolio.controlegastos.infrastructure.repository;

import com.portifolio.controlegastos.infrastructure.entity.Lancamento;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    boolean existsByCategoriaId(Long id);

    @EntityGraph(attributePaths = "categoria")
    List<Lancamento> findByUsuarioEmailOrderByDataDesc(String email);

    @EntityGraph(attributePaths = "categoria")
    Optional<Lancamento> findByIdAndUsuarioEmail(Long id, String email);

    @EntityGraph(attributePaths = "categoria")
    List<Lancamento> findByUsuarioEmailAndDataBetweenOrderByDataDesc(
            String email, LocalDate inicio, LocalDate fim);

    @Query("""
            SELECT c.tipoEnum, SUM(l.valor)
            FROM Lancamento l
            JOIN l.categoria c
            WHERE l.usuario.email = :email
              AND l.data BETWEEN :inicio AND :fim
            GROUP BY c.tipoEnum
            """)
    List<Object[]> somarPorTipo(@Param("email") String email,
                                @Param("inicio") LocalDate inicio,
                                @Param("fim") LocalDate fim);


}
