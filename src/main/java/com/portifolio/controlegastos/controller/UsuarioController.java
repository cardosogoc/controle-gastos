
package com.portifolio.controlegastos.controller;

import com.portifolio.controlegastos.business.dto.usuario.*;
import com.portifolio.controlegastos.business.service.UsuarioService;
import com.portifolio.controlegastos.controller.docs.UsuarioControllerDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController implements UsuarioControllerDocs {

    private final UsuarioService service;

    @Override
    @PostMapping
    public ResponseEntity<UsuarioResponse> criarUsuario(UsuarioRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criarUsuario(dto));
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<String> login(LoginRequest dto) {
        return ResponseEntity.ok(service.login(dto));
    }

    @Override
    @PutMapping("/me")
    public ResponseEntity<UsuarioResponse> editarPerfil(UsuarioRequest dto,
                                                        @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(service.editarPerfil(token, dto));
    }

    @Override
    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> acessarPerfil(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(service.acessarPerfil(token));
    }

    @Override
    @DeleteMapping("/me")
    public ResponseEntity<Void> deletaUsuario(@RequestHeader("Authorization") String token) {
        service.deletaUsuario(token);
        return ResponseEntity.noContent().build();
    }
}
