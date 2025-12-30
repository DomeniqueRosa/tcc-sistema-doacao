package com.sistemadoacao.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("doacao")
public class HistoricoDoacao  extends HistoricoStatus{
    @ManyToOne(fetch = FetchType.LAZY) // Lazy para performance
    @JoinColumn(name = "doacao_id")
    private Doacao doacao;

}
