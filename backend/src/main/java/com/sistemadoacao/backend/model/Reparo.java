package com.sistemadoacao.backend.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Reparo {
    @Id
    private Long id;
    private Long doacaoId;
    private String descricao;
    private Long idTecnico;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    public Reparo() {
    }

    public Reparo(Long id, Long doacaoId, String descricao, Long idTecnico, LocalDate dataInicio, LocalDate dataFim) {
        this.id = id;
        this.doacaoId = doacaoId;
        this.descricao = descricao;
        this.idTecnico = idTecnico;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDoacaoId() {
        return doacaoId;
    }

    public void setDoacaoId(Long doacaoId) {
        this.doacaoId = doacaoId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(Long idTecnico) {
        this.idTecnico = idTecnico;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    

}
