package com.portifolio.controlegastos.controller;

import com.portifolio.controlegastos.business.dto.lancamento.LancamentoRequest;
import com.portifolio.controlegastos.business.dto.lancamento.LancamentoResponse;
import com.portifolio.controlegastos.business.service.LancamentoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lancamento")
@RequiredArgsConstructor
public class LancamentoController {

    private final LancamentoService service;

    @PostMapping
    public ResponseEntity<LancamentoResponse> criarLancamento(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody LancamentoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarLancamento(token, dto));
    }

    @GetMapping
    public ResponseEntity<List<LancamentoResponse>> listarLancamentoUsuario(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(service.listarLancamentos(token));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LancamentoResponse> editarLancamento(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @Valid @RequestBody LancamentoRequest dto) {
        return ResponseEntity.ok(service.editaLancamento(token, dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletaLancamento(@RequestHeader("Authorization") String token,
                                                 @PathVariable Long id) {
        service.deletaLancamento(token, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mes")
    public ResponseEntity<List<LancamentoResponse>> listarPorMes(
            @RequestHeader("Authorization") String token,
            @RequestParam int ano,
            @RequestParam @Min(1) @Max(12) int mes) {
        return ResponseEntity.ok(service.listarPorMes(token, ano, mes));
    }
}
