package com.sistemadoacao.backend.dto;

import java.time.LocalDateTime;

public record ReparoDTO(Long id_doacao, String descricao, LocalDateTime dtFim) {

}
