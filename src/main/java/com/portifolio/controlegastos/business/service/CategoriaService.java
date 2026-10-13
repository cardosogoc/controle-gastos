package com.portifolio.controlegastos.business.service;

import com.portifolio.controlegastos.business.dto.categoria.CategoriaRequest;
import com.portifolio.controlegastos.business.dto.categoria.CategoriaResponse;
import com.portifolio.controlegastos.business.mapper.CategoriaMapper;
import com.portifolio.controlegastos.exception.ConflictException;
import com.portifolio.controlegastos.exception.NotFoundException;
import com.portifolio.controlegastos.infrastructure.entity.Categoria;
import com.portifolio.controlegastos.infrastructure.repository.CategoriaRepository;
import com.portifolio.controlegastos.infrastructure.repository.UsuarioRepository;
import com.portifolio.controlegastos.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaMapper mapper;
    private final JwtUtil jwtUtil;

    public CategoriaResponse criarCategoria(String token, CategoriaRequest dto){

        String email = jwtUtil.extrairEmailToken(token.substring(7));
        if (verificaCategoriaExistente(dto, email)){
            throw new ConflictException("Categoria com o tipo: " + dto.tipoEnum() + " E de nome: " + dto.nome() + " já é existente");
        }

        Categoria entity = mapper.paraCategoriaEntity(dto);
        entity.setUsuario(usuarioRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Usuário não encontrado")));

        return mapper.paraCategoriaDTO(categoriaRepository.save(entity));
    }

    private boolean verificaCategoriaExistente(CategoriaRequest dto, String email) {

        return categoriaRepository.existsByNomeAndTipoEnumAndUsuarioEmail(
                dto.nome(),
                dto.tipoEnum(),
                email
        );
    }
}
