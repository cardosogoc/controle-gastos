package com.portifolio.controlegastos.business.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.portifolio.controlegastos.business.dto.usuario.LoginRequest;
import com.portifolio.controlegastos.business.dto.usuario.UsuarioRequest;
import com.portifolio.controlegastos.business.dto.usuario.UsuarioResponse;
import com.portifolio.controlegastos.business.mapper.UsuarioMapper;
import com.portifolio.controlegastos.exception.UnauthorizedException;
import com.portifolio.controlegastos.infrastructure.entity.Usuario;
import com.portifolio.controlegastos.infrastructure.repository.UsuarioRepository;
import com.portifolio.controlegastos.infrastructure.security.GoogleTokenVerifier;
import com.portifolio.controlegastos.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;
    private final JwtUtil jwtUtil;
    private final GoogleTokenVerifier googleTokenVerifier;

    public String login(LoginRequest dto) {
        GoogleIdToken.Payload payload = googleTokenVerifier.verificar(dto.idToken());

        if (!Boolean.TRUE.equals(payload.getEmailVerified())) {
            throw new UnauthorizedException("E-mail do Google não verificado");
        }

        Usuario usuario = repository.findByGoogleId(payload.getSubject())
                .orElseGet(() -> cadastrar(payload));

        return "Bearer " + jwtUtil.generateToken(usuario.getEmail());
    }

    private Usuario cadastrar(GoogleIdToken.Payload payload) {
        String nome = (String) payload.get("name");
        if (nome == null || nome.isBlank()) {
            nome = payload.getEmail();
        }

        Usuario novo = new Usuario();
        novo.setGoogleId(payload.getSubject());
        novo.setEmail(payload.getEmail());
        novo.setNome(nome);
        return repository.save(novo);
    }

    public UsuarioResponse editarPerfil(String token, UsuarioRequest dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        Usuario entity = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email não localizado"));

        mapper.atualizarUsuario(dto, entity);

        return mapper.paraUsuarioDTO(repository.save(entity));
    }

    public UsuarioResponse acessarPerfil(String token) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        Usuario entity = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Não foi possível acessar o perfil"));
        return mapper.paraUsuarioDTO(entity);
    }

    public void deletaUsuario(String token) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        Usuario entity = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Não foi possível acessar o perfil"));
        repository.deleteById(entity.getId());
    }
}