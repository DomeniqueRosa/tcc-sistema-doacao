package com.sistemadoacao.backend.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.sistemadoacao.backend.model.Usuario;
import com.sistemadoacao.backend.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    
   
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario saveUsuario(Usuario usuario) {

        // TODO
        //colocar validações aqui se existe usuário com mesmo email, etc
        Usuario novo = new Usuario();
        novo.setNome(usuario.getNome());
        novo.setCpf(usuario.getCpf());
        novo.setEmail(usuario.getEmail());
        // TODO: encryptar a senha
        // TODO: adc tratamento de senha
        novo.setSenha(usuario.getSenha());

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

}
