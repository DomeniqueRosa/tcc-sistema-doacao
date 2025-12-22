package com.sistemadoacao.backend.dto;
import com.sistemadoacao.backend.model.Pessoa;
// unico dto para representar os tipos de usuarios pois eles compartilham os mesmos atributos por enquanto
public record UsuarioDTO(
    Long id,
    String nome,
    String cpf,
    String email,
    String perfil
) {
    public UsuarioDTO(Pessoa pessoa) {
        this(
            pessoa.getId(),
            pessoa.getNome(),
            pessoa.getCpf(),
            pessoa.getEmail(),
            pessoa.getClass().getSimpleName()
        );
    }

}
