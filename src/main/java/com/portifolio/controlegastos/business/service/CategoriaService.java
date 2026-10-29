package com.portifolio.controlegastos.business.service;

import com.portifolio.controlegastos.business.dto.categoria.CategoriaRequest;
import com.portifolio.controlegastos.business.dto.categoria.CategoriaResponse;
import com.portifolio.controlegastos.business.mapper.CategoriaMapper;
import com.portifolio.controlegastos.exception.ConflictException;
import com.portifolio.controlegastos.exception.NotFoundException;
import com.portifolio.controlegastos.infrastructure.entity.Categoria;
import com.portifolio.controlegastos.infrastructure.entity.Usuario;
import com.portifolio.controlegastos.infrastructure.repository.CategoriaRepository;
import com.portifolio.controlegastos.infrastructure.repository.LancamentoRepository;
import com.portifolio.controlegastos.infrastructure.repository.UsuarioRepository;
import com.portifolio.controlegastos.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private final LancamentoRepository lancamentoRepository;
    private final CategoriaMapper mapper;
    private final JwtUtil jwtUtil;

    public CategoriaResponse criarCategoria(String token, CategoriaRequest dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        String nome = dto.nome().trim();

        if (categoriaRepository.existsByNomeAndTipoEnumAndUsuarioEmail(nome, dto.tipoEnum(), email)) {
            throw new ConflictException(
                    "Já existe uma categoria de " + dto.tipoEnum() + " chamada '" + nome + "'");
        }

        Categoria entity = mapper.paraCategoriaEntity(dto);
        entity.setNome(nome);
        entity.setUsuario(usuario);

        return mapper.paraCategoriaDTO(categoriaRepository.save(entity));
    }

    private boolean verificaCategoriaExistente(CategoriaRequest dto, String email) {

        return categoriaRepository.existsByNomeAndTipoEnumAndUsuarioEmail(
                dto.nome(),
                dto.tipoEnum(),
                email
        );
    }

    public List<CategoriaResponse> listarCategoriasUsuario(String token) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        return mapper.paraListaCategoriasDTO(categoriaRepository.findByUsuarioEmail(email));
    }

    public void deletaCategoria(String token, Long id) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        Categoria entity = categoriaRepository.findByIdAndUsuarioEmail(id, email)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));

        if (lancamentoRepository.existsByCategoriaId(id)) {
            throw new ConflictException("Categoria possui lançamentos e não pode ser excluída");
        }

        categoriaRepository.delete(entity);
    }


    public CategoriaResponse editaCategoria(String token, CategoriaRequest dto, Long id) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        Categoria entity = categoriaRepository.findByIdAndUsuarioEmail(id, email)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));

        String nome = dto.nome().trim();

        if (categoriaRepository.existsByNomeAndTipoEnumAndUsuarioEmailAndIdNot(
                nome, dto.tipoEnum(), email, id)) {
            throw new ConflictException(
                    "Já existe uma categoria de " + dto.tipoEnum() + " chamada '" + nome + "'");
        }

        mapper.updateCategoria(dto, entity);
        entity.setNome(nome);

        return mapper.paraCategoriaDTO(categoriaRepository.save(entity));
    }
}
