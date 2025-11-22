package com.sistemadoacao.backend.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sistemadoacao.backend.model.Usuario;
import com.sistemadoacao.backend.repository.UsuarioRepository;

import jakarta.transaction.Transactional;



@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(UsuarioService.class);
    
   
    public UsuarioService(UsuarioRepository usuarioRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario saveUsuario(Usuario usuario) {
        Usuario novo = new Usuario();

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Já existe um usuário cadastrado com este e-mail.");
        }

        try {
            novo.setNome(usuario.getNome());
            novo.setCpf(usuario.getCpf());
            novo.setEmail(usuario.getEmail());
            String senhaCript = passwordEncoder.encode(usuario.getSenha());

            novo.setSenha(senhaCript);
            usuarioRepository.save(novo);
          
            String assunto = "Cadastro realizado com sucesso!";
            String texto = "Olá " + novo.getNome() + ",\n\nSeu cadastro no sistema de doações foi realizado com sucesso!\n\nObrigado por se juntar a nós.\n\nAtenciosamente,\nEquipe do Sistema de Doações";
            emailService.enviarEmailCadastro(novo.getEmail(), assunto, texto);
            
        } catch (Exception e) {
            logger.error("Erro ao enviar e-mail de cadastro: {}", e.getMessage());
        }
        logger.info("Salvando novo usuário: {}", usuario.getEmail(), usuario.getSenha());
        return novo;
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

}
