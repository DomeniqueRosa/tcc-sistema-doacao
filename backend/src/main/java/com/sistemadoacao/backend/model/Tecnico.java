package com.sistemadoacao.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Tecnico extends Pessoa {

    @Schema(description = "Codido do aluno GRR", example = "GRR20201010")
    private String grr;
    @Schema(description = "Nome do curso", example = "Análise e Desenvolvimento de Sistemas")
    private String curso;

}
