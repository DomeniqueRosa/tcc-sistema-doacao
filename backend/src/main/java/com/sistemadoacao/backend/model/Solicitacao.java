package com.sistemadoacao.backend.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Solicitacao {
    @Id
    private Long id;

    private String cpfUsuario;

    private String curso;

    private String grr;

    private String motivo;

    private LocalDate dataCadastro;

    private Status status;

    @OneToMany(mappedBy = "solicitacao", cascade = CascadeType.ALL)
    private List<HistoricoSolicitacao> historico; // Relação com HistoricoSolicitacao

    @OneToMany(mappedBy = "solicitacao", cascade = CascadeType.ALL)
    private List<Doacao> doacoes = new ArrayList<>();


    public Solicitacao() {
    }

    public Solicitacao(Long id, String cpfUsuario, String curso, String grr, String motivo, LocalDate dataCadastro,
            Status status, List<Doacao> doacoes, List<HistoricoSolicitacao> historico) {
        this.id = id;
        this.cpfUsuario = cpfUsuario;
        this.curso = curso;
        this.grr = grr;
        this.motivo = motivo;
        this.dataCadastro = dataCadastro;
        this.status = status;
        this.doacoes = doacoes;
        this.historico = historico;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpfUsuario() {
        return cpfUsuario;
    }

    public void setCpfUsuario(String cpfUsuario) {
        this.cpfUsuario = cpfUsuario;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getGrr() {
        return grr;
    }

    public void setGrr(String grr) {
        this.grr = grr;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Doacao> getDoacoes() {
        return doacoes;
    }

    public void setDoacoes(List<Doacao> doacoes) {
        this.doacoes = doacoes;
    }

    public List<HistoricoSolicitacao> getHistorico() {
        return historico;
    }

    public void setHistorico(List<HistoricoSolicitacao> historico) {
        this.historico = historico;
    }

    

}
