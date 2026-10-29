package com.portifolio.controlegastos.controller;

import com.portifolio.controlegastos.business.dto.categoria.CategoriaRequest;
import com.portifolio.controlegastos.business.dto.categoria.CategoriaResponse;
import com.portifolio.controlegastos.business.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
@RequiredArgsConstructor
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @PostMapping
    public ResponseEntity<CategoriaResponse> criarCategoria(@RequestBody CategoriaRequest dto,
                                                            @RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarCategoria(token, dto));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listarCategoriasUsuario(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(service.listarCategoriasUsuario(token));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletaCategoria(@RequestHeader("Authorization") String token,
                                                @PathVariable Long id) {
        service.deletaCategoria(token, id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponse> editaCategoria(@RequestHeader("Authorization") String token,
                                                            @PathVariable Long id,
                                                            @Valid @RequestBody CategoriaRequest dto) {
        return ResponseEntity.ok(service.editaCategoria(token, dto, id));
    }
}
