package com.portifolio.controlegastos.business.dto.usuario;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank String idToken) {
}