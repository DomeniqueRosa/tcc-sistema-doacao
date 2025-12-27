package com.sistemadoacao.backend.service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sistemadoacao.backend.model.Pessoa;
import com.sistemadoacao.backend.repository.PessoaRepository;

@Service
public class LoginService implements UserDetailsService {

    private final PessoaRepository repository;

    public LoginService(PessoaRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Pessoa pessoa = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        // Pega o tipo da classe e define a Role
        String role = "ROLE_" + pessoa.getClass().getSimpleName().toUpperCase();

        return User.builder()
                .username(pessoa.getEmail())
                .password(pessoa.getSenha())
                .authorities(role)
                .build();
    }
}
