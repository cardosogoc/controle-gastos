package com.portifolio.controlegastos.controller;

import com.portifolio.controlegastos.business.dto.resumo.ResumoMensalResponse;
import com.portifolio.controlegastos.business.service.ResumoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resumo")
@RequiredArgsConstructor
public class ResumoController {

    private final ResumoService service;

    @GetMapping("/mensal")
    public ResponseEntity<ResumoMensalResponse> resumoMensal(
            @RequestHeader("Authorization") String token,
            @RequestParam int ano,
            @RequestParam int mes) {
        return ResponseEntity.ok(service.resumoMensal(token, ano, mes));
    }
}