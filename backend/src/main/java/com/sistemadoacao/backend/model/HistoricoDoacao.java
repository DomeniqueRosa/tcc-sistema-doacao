package com.sistemadoacao.backend.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
@DiscriminatorValue("doacao")
public class HistoricoDoacao  extends HistoricoStatus{
    @ManyToOne
    @JoinColumn(name = "doacao_id")
    @JsonIgnore
    private Doacao doacao;

    public HistoricoDoacao(Long id, LocalDateTime dataAlteracao, String observacao, String executor, Status status,
            Doacao doacao) {
        super(id, dataAlteracao, observacao, executor, status);
        this.doacao = doacao;
    }

    public HistoricoDoacao(Doacao doacao) {
        this.doacao = doacao;
    }

    public HistoricoDoacao(){}

    public Doacao getDoacao() {
        return doacao;
    }

    public void setDoacao(Doacao doacao) {
        this.doacao = doacao;
    }

    

    

}
