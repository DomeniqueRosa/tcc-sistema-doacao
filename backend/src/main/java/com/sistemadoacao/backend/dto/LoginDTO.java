package com.sistemadoacao.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(@NotBlank String token, String email,  @NotBlank String perfil) {


}
