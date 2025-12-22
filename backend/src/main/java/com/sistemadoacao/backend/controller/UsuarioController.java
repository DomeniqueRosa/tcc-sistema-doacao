package com.sistemadoacao.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sistemadoacao.backend.dto.UsuarioDTO;
import com.sistemadoacao.backend.model.Usuario;
import com.sistemadoacao.backend.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")

public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar usuário", description = "Cadastra um novo usuário no banco de dados com email unico. ")
    @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso")
    @ApiResponse(responseCode = "409", description = "Email já cadastrado", content = @Content)
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    public ResponseEntity<UsuarioDTO> cadastrarUsuario(@RequestBody Usuario usuario) {
        UsuarioDTO novoUsuario;
        try {
            novoUsuario = usuarioService.saveUsuario(usuario);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @GetMapping()
    @Operation(summary = "Listar todos os usuários")
    @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.getAllUsuarios());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Listar usuário por ID")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    public ResponseEntity<UsuarioDTO> listarUsuarioPorId(@PathParam(value = "") Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Usuario usuarioEntidade = usuarioService.getUsuarioById(id);
            UsuarioDTO usuario = new UsuarioDTO(usuarioEntidade);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário pelo ID")
    @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<Void> deletarUsuario(@PathParam(value = "") Long id) {
        boolean deleted = false;
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        deleted = usuarioService.deleteUsuario(id);
        if (deleted) {
            // 204 No Content
            return ResponseEntity.noContent().build();
        } else {
            // 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar usuário pelo ID", description = "Atualiza os dados de um usuário existente pelo ID.")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content)
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {

        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        Usuario existente;
        try {
            existente = usuarioService.getUsuarioById(id);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }

        if (usuario.getNome() != null) {
            existente.setNome(usuario.getNome());
        }
        if (usuario.getEmail() != null) {
            existente.setEmail(usuario.getEmail());
        }

        if (usuario.getCpf() != null) {
            existente.setCpf(usuario.getCpf());
        }

        if (usuario.getSenha() != null) {
            existente.setSenha(usuario.getSenha());
        }

        Usuario usuarioAtualizado = usuarioService.updateUsuario(id, existente);
        return ResponseEntity.ok(usuarioAtualizado);

    }

}
