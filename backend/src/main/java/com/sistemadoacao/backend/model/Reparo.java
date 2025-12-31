package com.sistemadoacao.backend.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Reparo {
    @Id
    private Long id;
    private String descricao;
    private Long idTecnico;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    @OneToOne
    @JoinColumn(name = "doacao_id") // Nome da coluna no banco de dados
    private Doacao doacao; 


    public Reparo() {
    }

    public Reparo(Long id, String descricao, Long idTecnico, LocalDate dataInicio, LocalDate dataFim, Doacao doacao) {
        this.id = id;
        this.descricao = descricao;
        this.idTecnico = idTecnico;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.doacao = doacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Doacao getDoacao() {
        return doacao;
    }

    public void setDoacao(Doacao doacao) {
        this.doacao = doacao;
    }

    

}
