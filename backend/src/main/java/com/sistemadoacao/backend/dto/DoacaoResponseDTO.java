package com.sistemadoacao.backend.dto;

import org.springframework.web.multipart.MultipartFile;

import com.sistemadoacao.backend.model.Equipamento;
import com.sistemadoacao.backend.model.Status;

public record DoacaoResponseDTO(
        Equipamento equipamento,
        Integer quantidade,
        String descricao,
        Status status,
        MultipartFile imagem
) {

}
