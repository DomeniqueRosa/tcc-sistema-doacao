package com.sistemadoacao.backend.dto;

import com.sistemadoacao.backend.model.Status;

public record AnaliseIAResponse(
    Status status,
    String descricao,
    String recomendacao
) {

}
