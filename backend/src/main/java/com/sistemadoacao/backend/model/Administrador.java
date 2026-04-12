package com.sistemadoacao.backend.model;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@OnDelete(action = OnDeleteAction.CASCADE)
public class Administrador extends Pessoa {
    
}
