package com.sistemadoacao.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemadoacao.backend.model.Doacao;

@Repository
public interface DoacaoRepository extends JpaRepository<Doacao, Long> {

}
