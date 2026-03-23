package com.sistemadoacao.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemadoacao.backend.model.ImagemDoacao;

@Repository
public interface ImagemRepository extends JpaRepository<ImagemDoacao, Long> {

}
