package com.sistemadoacao.backend.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@OnDelete(action = OnDeleteAction.CASCADE)
public class Tecnico extends Pessoa {

    @Schema(description = "Codido do aluno GRR", example = "GRR20201010")
    private String grr;
    @Schema(description = "Nome do curso", example = "Análise e Desenvolvimento de Sistemas")
    private String curso;

}
