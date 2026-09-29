package com.portifolio.controlegastos.business.service;

import com.portifolio.controlegastos.business.dto.usuario.UsuarioRequest;
import com.portifolio.controlegastos.business.dto.usuario.UsuarioResponse;
import com.portifolio.controlegastos.business.mapper.UsuarioMapper;
import com.portifolio.controlegastos.infrastructure.entity.Usuario;
import com.portifolio.controlegastos.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioResponse criarUsuario(UsuarioRequest dto){
        emailExiste(dto.email());
        Usuario entity = mapper.paraUsuarioEntity(dto);
        entity.setSenha(passwordEncoder.encode(dto.senha()));
        return mapper.paraUsuarioDTO(repository.save(entity));
    }

    public void emailExiste(String email){
        try{
            boolean existe = verificaEmailExistente(email);
            if (existe){
                throw new RuntimeException("Email já cadastrado" + email);
            }
        } catch (RuntimeException e){
            throw new RuntimeException("Email já cadastrado" + e);
        }
    }

    private boolean verificaEmailExistente(String email) {
        return repository.existsByEmail(email);
    }



}
