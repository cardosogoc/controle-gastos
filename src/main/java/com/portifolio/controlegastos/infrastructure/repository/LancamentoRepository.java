package com.portifolio.controlegastos.infrastructure.repository;

import com.portifolio.controlegastos.infrastructure.entity.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
    
}
