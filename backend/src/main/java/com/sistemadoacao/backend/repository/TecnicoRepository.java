package com.sistemadoacao.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistemadoacao.backend.model.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Long>{
    
}
