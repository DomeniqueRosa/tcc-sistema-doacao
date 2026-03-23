package com.sistemadoacao.backend.model;

import java.time.LocalDateTime;


import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "historico_status")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_entidade")
public abstract class HistoricoStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataAlteracao = LocalDateTime.now();
    
    private String observacao; // Parecer da avaliação
    
    private String executor; // Quem mudou (Admin/Tecnico)

    @Enumerated(EnumType.STRING)
    private Status status;

    public HistoricoStatus() {
    }

    

    public HistoricoStatus(Long id, LocalDateTime dataAlteracao, String observacao, String executor, Status status) {
        this.id = id;
        this.dataAlteracao = dataAlteracao;
        this.observacao = observacao;
        this.executor = executor;
        this.status = status;
    }

    


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }



    public Status getStatus() {
        return status;
    }



    public void setStatus(Status status) {
        this.status = status;
    }

    

}
