package com.sistemadoacao.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sistemadoacao.backend.dto.UsuarioDTO;
import com.sistemadoacao.backend.model.Administrador;
import com.sistemadoacao.backend.model.Pessoa;
import com.sistemadoacao.backend.model.Tecnico;
import com.sistemadoacao.backend.model.Usuario;
import com.sistemadoacao.backend.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
@Slf4j
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Operation(summary = "Cadastro de usuario Administrador", description = "Cadastra um novo usuario no banco com permissao de administrador")
    @ApiResponse(responseCode = "403", description = "Não autorizado, apenas usuario com permissao de ADMINISTRADOR pode cadastrar um novo administrador.", content = @Content)
    public ResponseEntity<Administrador> cadastrarAdmin(@RequestBody Administrador admin) {
        Administrador novo;
        try {
            novo = usuarioService.saveAdmin(admin);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @PostMapping("/tecnico")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Operation(summary = "Cadastro de usuario Tecnico", description = "Cadastra um novo usuario no banco com permissao de tecnico")
    @ApiResponse(responseCode = "403", description = "Não autorizado, apenas usuario com permissao de ADMIN pode cadastrar um novo tecnico.", content = @Content)
    public ResponseEntity<Tecnico> cadastrarTecnico(@RequestBody Tecnico tecnico) {
        Tecnico novo;

        try {

            novo = usuarioService.saveTecnico(tecnico);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }
    

    @PostMapping
    @Operation(summary = "Cadastrar usuário", description = "Cadastra um novo usuário no banco de dados com email unico. ")
    @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
            {
                "nome": "João Silva",
                "cpf": "12345678900",
                "email": "joao@gmail.com",
                "senha": "senhaSegura123"
            }
            """)))
    @ApiResponse(responseCode = "409", description = "Email já cadastrado", content = @Content)
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    public ResponseEntity<UsuarioDTO> cadastrarUsuario(@RequestBody Usuario usuario) {
        UsuarioDTO novoUsuario;
        try {
            log.info("iniciando cadastro" + usuario);
            novoUsuario = usuarioService.saveUsuario(usuario);

        } catch (Exception e) {
            log.error("erro:" + e);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @GetMapping()
    // TODO: Adicionar @PreAuthorize("hasRole('ADMINISTRADOR')") pois só adm pode ver todos os usuarios
    @Operation(summary = "Listar todos os usuários")
    @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    // TODO: Retornar uma PessoaResponseDTO
    public ResponseEntity<List<Pessoa>> listarUsuarios() {
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
    @PreAuthorize("hasRole('ADMINISTRADOR') or #id == authentication.principal.id")
    @Operation(summary = "Deletar usuário pelo ID", description = "Excluir usuario permitido apenas para ADMINISTRADOR ou para o próprio usuário dono do ID")
    @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
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
    @PreAuthorize("hasRole('ADMINISTRADOR') or #id == authentication.principal.id")
    @Operation(summary = "Atualizar usuário pelo ID", description = "Atualiza os dados de um usuário existente pelo ID permitido pelo ADMINISTRADOR ou usuario do ID.")
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
