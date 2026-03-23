package com.sistemadoacao.backend.dto;

public class GraficoDTO {
    Integer mes;
    Long total;

    public GraficoDTO(Integer mes, Long total) {
        this.mes = mes;
        this.total = total;
    }

    public GraficoDTO() {
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    
}
