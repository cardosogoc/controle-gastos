package com.portifolio.controlegastos.business.service;


import com.portifolio.controlegastos.business.dto.lancamento.LancamentoRequest;
import com.portifolio.controlegastos.business.dto.lancamento.LancamentoResponse;
import com.portifolio.controlegastos.business.mapper.LancamentoMapper;
import com.portifolio.controlegastos.exception.NotFoundException;
import com.portifolio.controlegastos.infrastructure.entity.Categoria;
import com.portifolio.controlegastos.infrastructure.entity.Lancamento;
import com.portifolio.controlegastos.infrastructure.entity.Usuario;
import com.portifolio.controlegastos.infrastructure.repository.CategoriaRepository;
import com.portifolio.controlegastos.infrastructure.repository.LancamentoRepository;
import com.portifolio.controlegastos.infrastructure.repository.UsuarioRepository;
import com.portifolio.controlegastos.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LancamentoService {

    private final LancamentoRepository lancamentoRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private final LancamentoMapper mapper;
    private final JwtUtil jwtUtil;

    public LancamentoResponse criarLancamento(String token, LancamentoRequest dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        Categoria categoria = categoriaRepository.findByIdAndUsuarioEmail(dto.categoriaId(), email)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));

        Lancamento entity = mapper.paraLancamentoEntity(dto);
        entity.setUsuario(usuario);
        entity.setCategoria(categoria);

        return mapper.paraLancamentoDTO(lancamentoRepository.save(entity));
    }

    public List<LancamentoResponse> listarLancamentos(String token) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        return mapper.listaLancamentoDTO(lancamentoRepository.findByUsuarioEmail(email));
    }

    public LancamentoResponse editaLancamento(String token, LancamentoRequest dto, Long id) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        Lancamento entity = lancamentoRepository.findByIdAndUsuarioEmail(id, email)
                .orElseThrow(() -> new NotFoundException("Lançamento não encontrado"));

        Categoria categoria = categoriaRepository.findByIdAndUsuarioEmail(dto.categoriaId(), email)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));

        mapper.updateLancamento(dto, entity);
        entity.setCategoria(categoria);

        return mapper.paraLancamentoDTO(lancamentoRepository.save(entity));
    }

    public void deletaLancamento(String token, Long id) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        Lancamento entity = lancamentoRepository.findByIdAndUsuarioEmail(id, email)
                .orElseThrow(() -> new NotFoundException("Lançamento não encontrado"));

        lancamentoRepository.delete(entity);
    }

    public List<LancamentoResponse> listarPorMes(String token, int ano, int mes) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        YearMonth periodo = YearMonth.of(ano, mes);
        LocalDate inicio = periodo.atDay(1);
        LocalDate fim = periodo.atEndOfMonth();

        return mapper.listaLancamentoDTO(
                lancamentoRepository.findByUsuarioEmailAndDataBetweenOrderByDataDesc(email, inicio, fim));
    }
}
