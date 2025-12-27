package com.sistemadoacao.backend.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemadoacao.backend.model.Doacao;
import com.sistemadoacao.backend.model.Equipamento;
import com.sistemadoacao.backend.model.Status;

@Repository
public interface DoacaoRepository extends JpaRepository<Doacao, Long> {

    // lista todos por equipamento
    List<Doacao> findByEquipamento(Equipamento equipamento);

    // Lista todos por status
    List<Doacao> findByStatus(Status status);

}
