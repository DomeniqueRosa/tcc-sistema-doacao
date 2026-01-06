package com.sistemadoacao.backend.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.sistemadoacao.backend.model.Pessoa;

@Component
public class Utils {


    public String getNomeUsuarioLogado() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Pessoa pessoa) {
            return pessoa.getNome(); 
        }
        return "Sistema"; 
    }

    public Long getIdUsuarioLogado(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Pessoa pessoa) {
            return pessoa.getId(); 
        }
        return null; 

    }

}
