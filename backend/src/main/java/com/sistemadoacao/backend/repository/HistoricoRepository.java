package com.sistemadoacao.backend.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sistemadoacao.backend.model.HistoricoStatus;

@Repository
public interface HistoricoRepository extends JpaRepository<HistoricoStatus, Long> {

}
