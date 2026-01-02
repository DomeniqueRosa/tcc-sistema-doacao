package com.sistemadoacao.backend.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.sistemadoacao.backend.model.Pessoa;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemadoacao.backend.dto.SolicitacaoRequest;
import com.sistemadoacao.backend.model.Solicitacao;
import com.sistemadoacao.backend.service.SolicitacaoService;

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
    public ResponseEntity<Solicitacao> criar(@RequestBody SolicitacaoRequest dto,
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
    public ResponseEntity<List<Solicitacao>> obterPorUsuario(@AuthenticationPrincipal Pessoa principal) {
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
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Solicitacao>> obterTodos() {
        log.info("Obtendo todas as solicitações");
        List<Solicitacao> solicitacoes = service.findAll(); // Ajuste conforme necessário
        return ResponseEntity.ok(solicitacoes);
    }


}
