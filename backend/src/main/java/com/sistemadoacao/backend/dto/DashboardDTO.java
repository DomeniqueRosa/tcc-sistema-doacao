package com.sistemadoacao.backend.dto;

import java.util.List;

public record DashboardDTO(
    long totalUsuarios,
    long totalDoacoes,
    long totalDoacoesRealizadas,
    long doacoesAprovadas,
    long doacoesAprovadasIA,    
    long doacoesReprovadas,   
    long doacoesReparo,
    List<GraficoDTO> doacoesPorMes,
    List<GraficoEquipamentoDTO> doacoesPorEquipamento
) {

    public DashboardDTO(long totalUsuarios, long totalDoacoes, long totalDoacoesRealizadas, long doacoesAprovadas, long doacoesAprovadasIA, long doacoesReprovadas,
            long doacoesReparo,  List<GraficoDTO> doacoesPorMes, List<GraficoEquipamentoDTO> doacoesPorEquipamento) {
        this.totalUsuarios = totalUsuarios;
        this.totalDoacoes = totalDoacoes;
        this.totalDoacoesRealizadas = totalDoacoesRealizadas;
        this.doacoesAprovadas = doacoesAprovadas;
        this.doacoesAprovadasIA = doacoesAprovadasIA;
        this.doacoesReprovadas = doacoesReprovadas;
        this.doacoesReparo= doacoesReparo;
        
        this.doacoesPorMes = doacoesPorMes;
        this.doacoesPorEquipamento = doacoesPorEquipamento;

    }

}
