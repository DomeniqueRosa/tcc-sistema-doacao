package com.sistemadoacao.backend.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sistemadoacao.backend.dto.UsuarioDTO;
import com.sistemadoacao.backend.model.Usuario;
import com.sistemadoacao.backend.repository.UsuarioRepository;

import org.springframework.lang.NonNull;
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

    // A anotação garante: salva tudo (Pessoa + Usuario), ou não salva nada.
    @Transactional
    public UsuarioDTO saveUsuario(Usuario usuario) {
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
        logger.info("Salvando novo usuário: {}", usuario.getEmail());
        return new UsuarioDTO(novo);
    }

    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioRepository.findAll().stream()
        .map(UsuarioDTO::new)
        .toList();
    }

    public Usuario getUsuarioById(@NonNull Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
        return usuario;
    }

   
    @Transactional
    public UsuarioDTO updateUsuario(@NonNull Long id, Usuario usuarioAtualizado) {
        
        Usuario usuarioExistente = getUsuarioById(id);

        if (!usuarioExistente.getEmail().equals(usuarioAtualizado.getEmail()) && 
            usuarioRepository.existsByEmail(usuarioAtualizado.getEmail())) {
            throw new RuntimeException("Este e-mail já está em uso por outro usuário.");
        }


        usuarioExistente.setNome(usuarioAtualizado.getNome());
        usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        usuarioExistente.setCpf(usuarioAtualizado.getCpf());

        UsuarioDTO usuarioDTO = new UsuarioDTO(usuarioRepository.save(usuarioExistente));

        return usuarioDTO;
    }

    public void deleteUsuario(@NonNull Long id) {
        usuarioRepository.deleteById(id);
    }

    @Transactional
    public void alterarSenha(@NonNull Long id, String senhaAtual, String novaSenha) {
        
        Usuario usuario = getUsuarioById(id);
        // Verifica se a senha atual está correta
        if (!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
            throw new RuntimeException("A senha atual informada está incorreta.");
        }

        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);
        
        logger.info("Senha alterada com sucesso para o usuário ID: {}", id);
    }

}
