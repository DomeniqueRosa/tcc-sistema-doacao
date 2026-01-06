package com.sistemadoacao.backend.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
public class Pessoa implements UserDetails {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @Schema(description = "Nome completo do usuário", example = "João Silva")
    private String nome;
    @Schema(description = "Número do CPF", example = "12345678900")
    private String cpf;
    @Schema(description = "Endereço de email do usuário", example = "joao@gmail.com")
    private String email;
    @Schema(description = "Senha do usuário", example = "senhaSegura123")
    private String senha;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDate dataCadastro;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role =  "ROLE_" + this.getClass().getSimpleName().toUpperCase();
        System.out.println("DEBUG: Role gerada para o usuário: " + role); // Verifique o console!
        return List.of(new SimpleGrantedAuthority(role));

    }
    @Override
    public String getPassword() {
        return this.senha;
    }
    @Override
    public String getUsername() {
        return this.email;
    }

    public Long getId(){
        return this.id;
    }

}
