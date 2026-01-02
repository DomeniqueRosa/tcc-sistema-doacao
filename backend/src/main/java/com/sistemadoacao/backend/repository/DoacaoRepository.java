package com.sistemadoacao.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sistemadoacao.backend.model.Doacao;
import com.sistemadoacao.backend.model.Equipamento;
import com.sistemadoacao.backend.model.Status;

@Repository
public interface DoacaoRepository extends JpaRepository<Doacao, Long> {

        // lista todos por equipamento
        List<Doacao> findByEquipamento(Equipamento equipamento);

        // Lista todas que tem status APROVADO ou APROVADO_IA
        @Query("SELECT d FROM Doacao d WHERE d.status = 'APROVADO' OR d.status = 'APROVADO_IA'")
        List<Object[]> findAprovadas();

        long countByStatus(Status status);

        @Query("SELECT MONTH(d.dataEntrega) as mes, COUNT(d.id) as total " +
                        "FROM Doacao d " +
                        "WHERE d.status = 'REALIZADA' " +
                        "GROUP BY MONTH(d.dataEntrega)")
        List<Object[]> findDoacoesMensais();

        @Query("SELECT d.equipamento, COUNT(d.id) " +
                        "FROM Doacao d " +
                        "GROUP BY d.equipamento")
        List<Object[]> findTotalPorEquipamento();

        List<Doacao> findByStatus(Status status);

}
