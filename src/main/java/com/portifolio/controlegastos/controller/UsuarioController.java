package com.portifolio.controlegastos.controller;

import com.portifolio.controlegastos.business.dto.usuario.LoginRequest;
import com.portifolio.controlegastos.business.dto.usuario.UsuarioRequest;
import com.portifolio.controlegastos.business.dto.usuario.UsuarioResponse;
import com.portifolio.controlegastos.business.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<UsuarioResponse> criarUsuario(@RequestBody UsuarioRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarUsuario(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest dto) {
        return ResponseEntity.ok().body(service.login(dto));
    }

    @PutMapping("/me")
    public ResponseEntity<UsuarioResponse> editarPerfil(@RequestBody UsuarioRequest dto,
                                                        @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(service.editarPerfil(token, dto));
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> acessarPerfil(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(service.acessarPerfil(token));
    }

    @DeleteMapping("me")
    public ResponseEntity<Void> deletaUsuario(@RequestHeader("Authorization") String token) {
        service.deletaUsuario(token);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
