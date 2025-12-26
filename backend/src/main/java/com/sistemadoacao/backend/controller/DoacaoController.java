package com.sistemadoacao.backend.controller;

import java.io.IOException;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import com.sistemadoacao.backend.model.Doacao;
import com.sistemadoacao.backend.model.Equipamento;
import com.sistemadoacao.backend.model.ImagemDoacao;
import com.sistemadoacao.backend.service.DoacaoService;
import com.sistemadoacao.backend.service.FileService;
import com.sistemadoacao.backend.model.Status;
import com.sistemadoacao.backend.model.Conservacao;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/doacao")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Doação", description = "Endpoints para gerenciamento de doacoes")
public class DoacaoController {

    private final DoacaoService doacaoService;
    private final FileService fileService;

    public DoacaoController(DoacaoService doacaoService, FileService file) {
        this.doacaoService = doacaoService;
        this.fileService = file;

    }

    @GetMapping()
    @Operation(summary = "Listar todas as doações", description = "Retorna uma lista de todas as doações cadastradas no sistema.")
    @ApiResponse(responseCode = "200", description = "Doacoes encontrados com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    public ResponseEntity<List<Doacao>> listarDoacoes() {

        try {
            return ResponseEntity.ok(doacaoService.listarDoacoes());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lista doacao por ID", description = "Retorna doacao com ID buscado.")
    @ApiResponse(responseCode = "200", description = "Doação encontrada com sucesso")
    @ApiResponse(responseCode = "404", description = "Doação nao encontrada.")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    public ResponseEntity<Doacao> listarDoacaoPorId(@PathVariable(value = "") Long id) {

        try {
            return ResponseEntity.ok(doacaoService.listarDoacaoPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e2) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Criar uma nova doação", description = "Cria uma nova doação com os dados fornecidos com upload da imagem associada.")
    @ApiResponse(responseCode = "201", description = "Doação criada com sucesso")
    @ApiResponse(responseCode = "404", description = "Arquivo não encontrado", content = @Content)
    @ApiResponse(responseCode = "415", description = "Formato de arquivo inválido", content = @Content)
    public ResponseEntity<Doacao> cadastrarDoacao(
            @RequestParam("cpfDoacao") String cpfDoacao,
            @RequestParam("equipamento") Equipamento equipamento,
            @RequestParam("quantidade") Integer quantidade,
            @RequestParam("descricao") String descricao,
            @RequestParam("statusConservacao") Conservacao statusConservacao,
            @RequestParam("arquivo") MultipartFile arquivo,
            @RequestParam("status") Status status) {

        try {
            String nomeArquivo = fileService.salvarArquivo(arquivo);

            // Imagem com a URL
            ImagemDoacao novaImagem = new ImagemDoacao(nomeArquivo);

            // Doacao e associar a Imagem

            Doacao novaDoacao = new Doacao();

            novaDoacao.setCpfUsuario(cpfDoacao);
            novaDoacao.setEquipamento(Equipamento.valueOf(equipamento.name()));
            novaDoacao.setQuantidade(quantidade);
            novaDoacao.setDescricao(descricao);
            novaDoacao.setStatusConservacao(Conservacao.valueOf(statusConservacao.name()));
            novaDoacao.setStatus(Status.valueOf(status.name()));
            novaDoacao.setImagem(novaImagem); // O CascadeType.ALL salvará a imagem automaticamente

            Doacao doacaoSalva = doacaoService.save(novaDoacao);

            return ResponseEntity.status(HttpStatus.CREATED).body(doacaoSalva);

        } catch (IOException e) {
            log.error("Erro ao salvar imagem", e);
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
        } catch (IllegalArgumentException e2) {

            e2.printStackTrace();
            log.error("Erro ao cadastrar doação", e2);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar doacao pelo ID")
    @ApiResponse(responseCode = "204", description = "Doação deletado com sucesso")
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @ApiResponse(responseCode = "404", description = "Doação não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<Void> deletarDoacao(@PathVariable Long id) {

        try {
            Doacao doacaoExistente = doacaoService.listarDoacaoPorId(id);
            // Tenta deletar o arquivo físico (se houver imagem)
            if (doacaoExistente.getImagem() != null && doacaoExistente.getImagem().getUrl() != null) {
                fileService.deletarArquivo(doacaoExistente.getImagem().getUrl());
            }

            // Deleta o registro do banco de dados
            doacaoService.deleteDoacao(id);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (RuntimeException e1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } catch (Exception e) {
            log.error("Erro ao deletar doação ou arquivo", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Atualização parcial", description = "Altera apenas os campos enviados no formulário.")
    @ApiResponse(responseCode = "201", description = "Doação criada com sucesso")
    @ApiResponse(responseCode = "404", description = "Arquivo não encontrado", content = @Content)
    @ApiResponse(responseCode = "415", description = "Formato de arquivo inválido", content = @Content)
    public ResponseEntity<Doacao> atualizarDoacao(
            @PathVariable Long id,
            @RequestParam(value = "cpfDoacao", required = false) String cpfDoacao,
            @RequestParam(value = "equipamento", required = false) Equipamento equipamento,
            @RequestParam(value = "quantidade", required = false) Integer quantidade,
            @RequestParam(value = "descricao", required = false) String descricao,
            @RequestParam(value = "statusConservacao", required = false) Conservacao statusConservacao,
            @RequestParam(value = "arquivo", required = false) MultipartFile arquivo,
            @RequestParam(value = "status", required = false) Status status) {

        try {
            // Busca a doação existente
            Doacao doacaoExistente = doacaoService.listarDoacaoPorId(id);
            if (doacaoExistente == null) {
                return ResponseEntity.notFound().build();
            }

            // Atualiza os campos que não forem nulos
            if (cpfDoacao != null)
                doacaoExistente.setCpfUsuario(cpfDoacao);
            if (equipamento != null)
                doacaoExistente.setEquipamento(equipamento);
            if (quantidade != null)
                doacaoExistente.setQuantidade(quantidade);
            if (descricao != null)
                doacaoExistente.setDescricao(descricao);
            if (statusConservacao != null)
                doacaoExistente.setStatusConservacao(statusConservacao);
            if (status != null)
                doacaoExistente.setStatus(status);

            // Lógica de imagem (Executada apenas se um novo arquivo for enviado)
            if (arquivo != null && !arquivo.isEmpty()) {

                // Deletar arquivo antigo para economizar espaço
                if (doacaoExistente.getImagem() != null) {
                    fileService.deletarArquivo(doacaoExistente.getImagem().getUrl());
                }

                String nomeArquivo = fileService.salvarArquivo(arquivo);

                // Atualizar entidade Imagem
                if (doacaoExistente.getImagem() != null) {
                    doacaoExistente.getImagem().setUrl(nomeArquivo);
                }
            }

            // Salva o que foi modificado
            Doacao doacaoAtualizada = doacaoService.save(doacaoExistente);
            return ResponseEntity.ok(doacaoAtualizada);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (RuntimeException e2) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
}
