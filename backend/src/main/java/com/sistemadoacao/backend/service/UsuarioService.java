package com.sistemadoacao.backend.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.sistemadoacao.backend.model.Usuario;
import com.sistemadoacao.backend.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;
    
   
    public UsuarioService(UsuarioRepository usuarioRepository, EmailService emailService) {
        this.usuarioRepository = usuarioRepository;
        this.emailService = emailService;
    }

    public Usuario saveUsuario(Usuario usuario) {

        // TODO
        //colocar validações aqui se existe usuário com mesmo email, etc

        try {
            Usuario novo = new Usuario();
            novo.setNome(usuario.getNome());
            novo.setCpf(usuario.getCpf());
            novo.setEmail(usuario.getEmail());
            // TODO: encryptar a senha
            // TODO: adc tratamento de senha
            novo.setSenha(usuario.getSenha());
            String assunto = "Cadastro realizado com sucesso!";
            String texto = "Olá " + novo.getNome() + ",\n\nSeu cadastro no sistema de doações foi realizado com sucesso!\n\nObrigado por se juntar a nós.\n\nAtenciosamente,\nEquipe do Sistema de Doações";
            emailService.enviarEmailSimples(novo.getEmail(), assunto, texto);
            
        } catch (Exception e) {
            System.out.println("Erro ao enviar email: " + e.getMessage());
        }

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

}
