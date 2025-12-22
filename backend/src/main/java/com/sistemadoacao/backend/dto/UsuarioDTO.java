package com.sistemadoacao.backend.dto;
import java.time.LocalDate;

import com.sistemadoacao.backend.model.Pessoa;

import io.swagger.v3.oas.annotations.media.Schema;
// unico dto para representar os tipos de usuarios pois eles compartilham os mesmos atributos por enquanto
public record UsuarioDTO(
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)// Esconde do Request Body
    Long id,
    @Schema(description = "Nome completo do usuário", example = "João Silva")
    String nome,
    @Schema(description = "Número do CPF", example = "12345678900")
    String cpf,
    @Schema(description = "Endereço de email do usuário", example = "joao@gmail.com")
    String email,
    String perfil,
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    LocalDate dataCadastro
) {
    public UsuarioDTO(Pessoa pessoa) {
        this(
            pessoa.getId(),
            pessoa.getNome(),
            pessoa.getCpf(),
            pessoa.getEmail(),
            pessoa.getClass().getSimpleName(),
            pessoa.getDataCadastro()
        );
    }

}
