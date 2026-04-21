package com.sistemadoacao.backend.dto;

import com.sistemadoacao.backend.model.Doacao;
import com.sistemadoacao.backend.model.Equipamento;
import com.sistemadoacao.backend.model.Status;

public record DoacaoResponseDTO(
        Long id,
        Equipamento equipamento,
        Integer quantidade,
        String descricao,
        Status status
) {
        public DoacaoResponseDTO(Doacao doacao) {
                this(doacao.getId(), doacao.getEquipamento(), doacao.getQuantidade(), doacao.getDescricao(), doacao.getStatus());
        }

}
