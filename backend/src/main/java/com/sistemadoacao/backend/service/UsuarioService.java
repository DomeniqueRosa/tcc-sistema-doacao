package com.sistemadoacao.backend.service;


import java.security.SecureRandom;
import java.util.List;



import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sistemadoacao.backend.dto.UsuarioDTO;
import com.sistemadoacao.backend.model.Administrador;
import com.sistemadoacao.backend.model.Pessoa;
import com.sistemadoacao.backend.model.Tecnico;
import com.sistemadoacao.backend.model.Usuario;
import com.sistemadoacao.backend.repository.AdministradorRepository;
import com.sistemadoacao.backend.repository.PessoaRepository;
import com.sistemadoacao.backend.repository.TecnicoRepository;
import com.sistemadoacao.backend.repository.UsuarioRepository;

import org.springframework.lang.NonNull;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class UsuarioService {
    
    private final PessoaRepository pessoaRepository;
    private final UsuarioRepository usuarioRepository;
    private final TecnicoRepository tecnicoRepository;
    private final AdministradorRepository admRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private static SecureRandom random = new SecureRandom();
    private static final String DATA_FOR_RANDOM = "abcdefghijklmnopqrstuvwxyz" + "abcdefghijklmnopqrstuvwxyz".toUpperCase() + "0123456789";
    
   
    public UsuarioService(UsuarioRepository usuarioRepository, EmailService emailService, PasswordEncoder passwordEncoder, TecnicoRepository tecnicoRepository, PessoaRepository pessoaRepository , AdministradorRepository admRepositor) {
        this.usuarioRepository = usuarioRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.tecnicoRepository = tecnicoRepository;
        this.pessoaRepository = pessoaRepository;
        this.admRepository = admRepositor;
    }

    // A anotação garante: salva tudo (Pessoa + Usuario), ou não salva nada.
    @Transactional
    public UsuarioDTO saveUsuario(Usuario usuario) {
        Usuario novo = new Usuario();

        if (pessoaRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Já existe um usuário cadastrado com este e-mail.");
        }

        try {
            novo.setNome(usuario.getNome());
            novo.setCpf(usuario.getCpf());
            novo.setEmail(usuario.getEmail());
            String senhaCript = passwordEncoder.encode(usuario.getSenha());

            novo.setSenha(senhaCript);
            usuarioRepository.save(novo);
          
            emailService.enviarEmailCadastro(novo.getEmail(), novo.getNome(), "");
            
        } catch (Exception e) {
            log.error("Erro ao enviar e-mail de cadastro: {}", e.getMessage());
        }
        log.info("Salvando novo usuário: {}", usuario.getEmail());
        return new UsuarioDTO(novo);
    }

    public List<Pessoa> getAllUsuarios() {
        return pessoaRepository.findAll().stream()
        .toList();
    }

    public Usuario getUsuarioById(@NonNull Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
        return usuario;
    }

   
    @Transactional
    public Usuario updateUsuario(@NonNull Long id, Usuario usuarioAtualizado) {
        
        Usuario usuarioExistente = getUsuarioById(id);

        if (!usuarioExistente.getEmail().equals(usuarioAtualizado.getEmail()) && 
            usuarioRepository.existsByEmail(usuarioAtualizado.getEmail())) {
            throw new RuntimeException("Este e-mail já está em uso por outro usuário.");
        }


        usuarioExistente.setNome(usuarioAtualizado.getNome());
        usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        usuarioExistente.setCpf(usuarioAtualizado.getCpf());

        Usuario usuario = usuarioRepository.save(usuarioExistente);

        return usuario;
    }

    public boolean deleteUsuario(@NonNull Long id) {
        if(usuarioRepository.existsById(id) == false) {
            log.warn("Tentativa de deletar usuário não existente: ID {}", id);
            return false;
        } else {
            usuarioRepository.deleteById(id);
            log.info("Usuário deletado com sucesso: ID {}", id);
            return true;
        }
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
        
        log.info("Senha alterada com sucesso para o usuário ID: {}", id);
    }

    public Tecnico saveTecnico(Tecnico tecnico) {
        // TODO: implementar alterar senha
        Tecnico novo = new Tecnico();

        if (pessoaRepository.existsByEmail(tecnico.getEmail())) {
            throw new RuntimeException("Já existe um usuário cadastrado com este e-mail.");
        }

        try {
            novo.setNome(tecnico.getNome());
            novo.setCpf(tecnico.getCpf());
            novo.setEmail(tecnico.getEmail());
            novo.setCurso(tecnico.getCurso());
            novo.setGrr(tecnico.getGrr());
            var novaSenha = gerarSenha(4);
            String senhaCript = passwordEncoder.encode(novaSenha);

            novo.setSenha(senhaCript);
            tecnicoRepository.save(novo);
          
            emailService.enviarEmailCadastro(novo.getEmail(), novo.getNome(), novaSenha);
            
        } catch (Exception e) {
            log.error("Erro ao enviar e-mail de cadastro: {}", e.getMessage());
        }
        log.info("Salvando novo usuário: {}", tecnico.getEmail());
        // TODO: Retornar um TecnicoResponseDTO
        return novo;
    }

    public List<Tecnico> listarTecnicos(){
        return tecnicoRepository.findAll().stream()
        .toList();
    }

    public String gerarSenha(int tamanho) {
        StringBuilder sb = new StringBuilder(tamanho);
        for (int i = 0; i < tamanho; i++) {
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM.length());
            char rndChar = DATA_FOR_RANDOM.charAt(rndCharAt);
            sb.append(rndChar);
        }
        return sb.toString();
    }

    public Administrador saveAdmin(Administrador adm) {
        Administrador novo = new Administrador();

        if (pessoaRepository.existsByEmail(adm.getEmail())) {
            throw new RuntimeException("Já existe um usuário cadastrado com este e-mail.");
        }

        try {
            novo.setNome(adm.getNome());
            novo.setCpf(adm.getCpf());
            novo.setEmail(adm.getEmail());
            String senhaCript = passwordEncoder.encode(adm.getSenha());
            novo.setSenha(senhaCript);

            admRepository.save(novo);
          
            emailService.enviarEmailCadastro(novo.getEmail(), novo.getNome(),"");
            
        } catch (Exception e) {
            log.error("Erro ao enviar e-mail de cadastro: {}", e.getMessage());
        }
        log.info("Salvando novo usuário: {}", novo.getEmail());
        // TODO: Retornar um AdmResponseDTO ou pessoaDto
        return novo;
        
    }

}
