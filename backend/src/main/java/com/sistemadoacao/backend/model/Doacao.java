package com.sistemadoacao.backend.model;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Doacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(example = "12345678900", description = "CPF do doador")
    private String cpfUsuario;

    @Schema(example = "Teclado", description = "Tipo de equipamento doado")
    private Equipamento equipamento;

    @Schema(example = "1", description = "Quantidade de equipamentos doados")
    private Integer quantidade;

    @Schema(example = "Equipamento em ótimo estado, pouco uso.", description = "Descrição do equipamento doado")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Schema(example = "NOVO", description = "Estado de conservação do equipamento doado")
    private Conservacao statusConservacao; 

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDate dataCadastro;

    @OneToOne(cascade = CascadeType.ALL)
    private ImagemDoacao imagem;

    @Enumerated(EnumType.STRING)
    @Schema(example = "PENDENTE", description = "Status da doação")
    private Status status;


}
