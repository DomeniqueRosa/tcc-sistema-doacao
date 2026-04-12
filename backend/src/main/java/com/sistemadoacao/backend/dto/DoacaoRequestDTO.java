package com.sistemadoacao.backend.dto;

import org.springframework.web.multipart.MultipartFile;

import com.sistemadoacao.backend.model.Conservacao;
import com.sistemadoacao.backend.model.Equipamento;

public record DoacaoRequestDTO(
    Equipamento equipamento,
    Integer quantidade,
    String descricao,
    Conservacao conservacao,
    MultipartFile imagem
) {

}
