package com.portifolio.controlegastos.business.service;

import com.portifolio.controlegastos.business.dto.usuario.LoginRequest;
import com.portifolio.controlegastos.business.dto.usuario.UsuarioRequest;
import com.portifolio.controlegastos.business.dto.usuario.UsuarioResponse;
import com.portifolio.controlegastos.business.mapper.UsuarioMapper;
import com.portifolio.controlegastos.exception.ConflictException;
import com.portifolio.controlegastos.exception.UnauthorizedException;
import com.portifolio.controlegastos.infrastructure.entity.Usuario;
import com.portifolio.controlegastos.infrastructure.repository.UsuarioRepository;
import com.portifolio.controlegastos.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

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
                throw new ConflictException("Email já cadastrado" + email);
            }
        } catch (ConflictException e){
            throw new ConflictException("Email já cadastrado" + e);
        }
    }

    private boolean verificaEmailExistente(String email) {
        return repository.existsByEmail(email);
    }


    public String login(LoginRequest dto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.email(),
                            dto.senha()));
            return "Bearer " + jwtUtil.generateToken(authentication.getName());
        } catch (BadCredentialsException | UsernameNotFoundException | AuthorizationDeniedException e){
            throw new UnauthorizedException("Usuario ou Senha inválidos", e);
        }
    }

    public UsuarioResponse editarPerfil(String token, UsuarioRequest dto) {

        String email = jwtUtil.extrairEmailToken(token.substring(7));

        Usuario entity = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email não localizado"));

        mapper.atualizarUsuario(dto, entity);

        if (dto.senha() != null && !dto.senha().isBlank()) {
            entity.setSenha(passwordEncoder.encode(dto.senha()));
        }

        return mapper.paraUsuarioDTO(repository.save(entity));
    }


}
