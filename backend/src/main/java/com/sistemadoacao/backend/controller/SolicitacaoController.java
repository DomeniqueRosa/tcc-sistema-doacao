package com.sistemadoacao.backend.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.sistemadoacao.backend.model.Pessoa;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemadoacao.backend.dto.SolicitacaoRequest;
import com.sistemadoacao.backend.model.Solicitacao;
import com.sistemadoacao.backend.service.SolicitacaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/solicitacao")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Solicitacao", description = "Endpoints para gerenciamento de solicitações")
public class SolicitacaoController {

    private final SolicitacaoService service;

    public SolicitacaoController(SolicitacaoService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Cadastra nova solicitação", description = "Cadastra uma nova solicitação associada ao usuário autenticado.")
    @ApiResponse(responseCode = "200", description = "Solicitacao salva com sucesso")
    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content)
    @ApiResponse(responseCode = "403", description = "Usuário não autenticado", content = @Content)
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    public ResponseEntity<Solicitacao> cadastrarSolicitacao(@RequestBody SolicitacaoRequest dto,
            @AuthenticationPrincipal Pessoa principal) {
        if (principal == null) {
            log.error("O objeto principal está nulo. Verifique se o SecurityFilter está injetando o usuário.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        log.info("Criando solicitação para o usuário ID: {}", principal.getId());
        

        try {
            return ResponseEntity.ok(service.save(dto, principal.getId()));
        } catch (Exception e) {
            log.error("Erro ao criar solicitação: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/usuario")
    @Operation(summary = "Lista solicitações do usuário autenticado", description = "Retorna todas as solicitações associadas ao usuário autenticado.")
    @ApiResponse(responseCode = "200", description = "Lista de solicitações retornada com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário não autenticado", content = @Content)
    @ApiResponse(responseCode = "404", description = "Nenhuma solicitação encontrada para o usuário", content = @Content)
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    public ResponseEntity<List<Solicitacao>> listarPorUsuario(@AuthenticationPrincipal Pessoa principal) {
        if (principal == null) {
            log.error("O objeto principal está nulo. Verifique se o SecurityFilter está injetando o usuário.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        log.info("Obtendo solicitação para o usuário ID: {}", principal.getId());

        List<Solicitacao> solicitacao = service.findByUsuarioId(principal.getId());
        if (solicitacao != null) {
            return ResponseEntity.ok(solicitacao);
        } else {
            log.warn("Nenhuma solicitação encontrada para o usuário ID: {}", principal.getId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    @Operation(summary = "Lista todas as solicitações", description = "Retorna todas as solicitações no sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de solicitações retornada com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    public ResponseEntity<List<Solicitacao>> listarTodos() {

        try {
            log.info("Obtendo todas as solicitações");
            List<Solicitacao> solicitacoes = service.findAll(); // Ajuste conforme necessário
            return ResponseEntity.ok(solicitacoes);
            
        } catch (Exception e) {
            log.error("Erro ao obter solicitações: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma solicitação pelo ID", description = "Deleta uma solicitação pelo ID fornecido.")
    @ApiResponse(responseCode = "204", description = "Solicitação deletada com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário não autenticado", content = @Content)
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    public ResponseEntity<Void> deletarSolicitacao(@PathVariable Long id) {
        log.info("Deletando solicitação ID: {}", id);
        try {
            service.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            log.error("Erro ao deletar solicitação ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualiza uma solicitação pelo ID", description = "Atualiza os dados de uma solicitação pelo ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Solicitação atualizada com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário não autenticado", content = @Content)
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    public ResponseEntity<Solicitacao> atualizarSolicitacao(@PathVariable Long id, @RequestBody SolicitacaoRequest dto) {
        log.info("Atualizando solicitação ID: {}", id);
        
        try {
            log.info("Dados que estao sendo passado:{}", dto);
            service.uptadeSolicitacao(id, dto);
            return ResponseEntity.status(HttpStatus.OK).build();
            
        } catch (Exception e) {
            log.info("Erro ao atualizar solicitação ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/aprovar/{id}")
    @Operation(summary = "Aprova uma solicitação pelo ID", description = "Marca a solicitação como aprovada pelo ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Solicitação aprovada com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário não autenticado", content = @Content)
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    public ResponseEntity<Solicitacao> aprovarSolicitacao(@PathVariable Long id) {
        log.info("Aprovando solicitação ID: {}", id);
        
        try {
            service.aprovarSolicitacao(id);
            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (Exception e) {
            log.error("Erro ao aprovar solicitação ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/reprovar/{id}")
    @Operation(summary = "Reprova uma solicitação pelo ID", description = "Marca a solicitação como reprovada pelo ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Solicitação reprovada com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário não autenticado", content = @Content)
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    public ResponseEntity<Solicitacao> reprovarSolicitacao(@PathVariable Long id) {
        log.info("Reprovando solicitação ID: {}", id);
        
        try {
            service.reprovarSolicitacao(id);
            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (Exception e) {
            log.error("Erro ao reprovar solicitação ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{solicitacaoId}/selecionar-doacao")
    @Operation(summary = "Seleciona uma doação para a solicitação", description = "Associa uma doação aprovada à solicitação pelo ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Doação selecionada com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário não autenticado", content = @Content)
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    public ResponseEntity<Solicitacao> selecionarDoacaoParaSolicitacao(
            @PathVariable Long solicitacaoId,
            @RequestBody Long doacaoId
        ) {
        log.info("Selecionando doação ID: {} para solicitação ID: {}", doacaoId, solicitacaoId);
        
        try {
            Solicitacao atualizado = service.selecionarDoacaoSolicitacao(solicitacaoId, doacaoId);
            return ResponseEntity.ok(atualizado);

        } catch (Exception e) {
            log.error("Erro ao selecionar doação ID {} para solicitação ID {}: {}", doacaoId, solicitacaoId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
