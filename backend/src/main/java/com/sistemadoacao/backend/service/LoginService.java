package com.sistemadoacao.backend.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.sistemadoacao.backend.repository.PessoaRepository;

@Service
public class LoginService implements UserDetailsService {

    private final PessoaRepository repository;

    public LoginService(PessoaRepository repository) {
        this.repository = repository;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}
