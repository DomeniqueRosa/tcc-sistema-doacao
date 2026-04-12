package com.sistemadoacao.backend.dto;

import com.sistemadoacao.backend.model.Equipamento;
import com.sistemadoacao.backend.model.Status;

public record DoacaoResponseDTO(
        Equipamento equipamento,
        Integer quantidade,
        String descricao,
        Status status
) {

}
