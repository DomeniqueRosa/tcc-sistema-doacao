package com.sistemadoacao.backend.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("solicitacao")
public class HistoricoSolicitacao extends HistoricoStatus {
    @ManyToOne
    @JoinColumn(name = "solicitacao_id")
    @JsonIgnore
    private Solicitacao solicitacao;

    public HistoricoSolicitacao(Long id, LocalDateTime dataAlteracao, String observacao, String executor, Status status,
            Solicitacao solicitacao) {
        super(id, dataAlteracao, observacao, executor, status);
        this.solicitacao = solicitacao;
    }

    public HistoricoSolicitacao(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }

    public HistoricoSolicitacao(){}

    public Solicitacao getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }

    


    
}

