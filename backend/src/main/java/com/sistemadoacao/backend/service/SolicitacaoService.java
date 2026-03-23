package com.sistemadoacao.backend.service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sistemadoacao.backend.dto.SolicitacaoRequestDTO;
import com.sistemadoacao.backend.model.Doacao;
import com.sistemadoacao.backend.model.HistoricoDoacao;
import com.sistemadoacao.backend.model.HistoricoSolicitacao;
import com.sistemadoacao.backend.model.Pessoa;
import com.sistemadoacao.backend.model.Solicitacao;
import com.sistemadoacao.backend.model.Status;
import com.sistemadoacao.backend.repository.DoacaoRepository;
import com.sistemadoacao.backend.repository.SolicitacaoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;
    private final DoacaoRepository doacaoRepository;

    public SolicitacaoService(SolicitacaoRepository solicitacaoRepository, DoacaoRepository doacaoRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.doacaoRepository = doacaoRepository;
    }

    public Solicitacao save(SolicitacaoRequestDTO solicitacao, Long usuarioId) {

        // Lógica de negócio: Solicitação sempre inicia com status PENDENTE
        Solicitacao solicitacaoEntity = new Solicitacao();
        solicitacaoEntity.setUsuarioId(usuarioId);
        solicitacaoEntity.setCurso(solicitacao.curso());
        solicitacaoEntity.setGrr(solicitacao.grr());
        solicitacaoEntity.setMotivo(solicitacao.motivo());
        solicitacaoEntity.setSem_computador(solicitacao.semComputador());
        solicitacaoEntity.setAtivo(solicitacao.ativo());
        solicitacaoEntity.setStatus(Status.PENDENTE);

        // Historico
        HistoricoSolicitacao h = new HistoricoSolicitacao();
        h.setDataAlteracao(LocalDateTime.now());
        h.setObservacao("Solicitação cadastrada no sistema.");
        h.setExecutor(getNomeUsuarioLogado());
        h.setStatus(Status.PENDENTE);

        h.setSolicitacao(solicitacaoEntity);
        
        solicitacaoEntity.getHistorico().add(h);

        log.info("Salvando solicitação para usuário ID: {}", usuarioId);
        log.debug("Detalhes da solicitação: {}", solicitacaoEntity);
        Solicitacao nova = solicitacaoRepository.save(solicitacaoEntity);

        return nova;
    }

    public List<Solicitacao> findByUsuarioId(Long id) {
        return solicitacaoRepository.findAllByUsuarioId(id);
    }

    public Solicitacao findById(Long id) throws Exception {
        return solicitacaoRepository.findById(id)
                .orElseThrow(() -> new Exception("Solicitação não encontrada com ID: " + id));
    }

    public List<Solicitacao> findAll() {
        return solicitacaoRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void delete(Long id) {

        
        // historico
        HistoricoSolicitacao h = new HistoricoSolicitacao();
        h.setDataAlteracao(LocalDateTime.now());
        h.setObservacao("Solicitacao deletada");
        h.setExecutor(getNomeUsuarioLogado());
        h.setStatus(Status.DESCARTE);
        // Adicionar id_solicitacao no historico
        try {
            Solicitacao s = findById(id);
            h.setSolicitacao(s);
            
        } catch (Exception e) {
            log.error("Erro ao buscar solicitacao para deletar.");
        }

        solicitacaoRepository.deleteById(id);
    }

    
    public Solicitacao uptadeSolicitacao(Long id, SolicitacaoRequestDTO dto) {
        try {
            Solicitacao existente = findById(id);

            if (existente != null) {

                if (dto.curso() != null) {
                    existente.setCurso(dto.curso());
                }
                if (dto.grr() != null) {
                    existente.setGrr(dto.grr());
                }
                if (dto.motivo() != null) {
                    existente.setMotivo(dto.motivo());
                }
                existente.setSem_computador(dto.semComputador());
                existente.setAtivo(dto.ativo());
            } else {
                log.warn("Solicitação com ID {} não encontrada para atualização.", id);
                return null;
            }

            log.info("Atualizando solicitação ID {} com novos dados: {}", id, existente);
            return solicitacaoRepository.save(existente);

        } catch (Exception e) {
            log.error("Erro ao atualizar solicitação ID {}: {}", id, e.getMessage());
            return null;

        }

    }

    @PreAuthorize("hasRole('USUARIO')")
    public void aprovarSolicitacao(Long id) {
        try {
            Solicitacao existente = findById(id);

            // Criar e Adicionar o Histórico
            HistoricoSolicitacao historico = new HistoricoSolicitacao();
            historico.setDataAlteracao(LocalDateTime.now());
            historico.setObservacao("Solicitação aprovada.");
            historico.setExecutor(getNomeUsuarioLogado());
            historico.setStatus(Status.APROVADO);

            historico.setSolicitacao(existente);

            existente.getHistorico().add(historico);
            existente.setStatus(Status.APROVADO);
            solicitacaoRepository.save(existente);
        } catch( AccessDeniedException e1){
            log.error("Usuario não tem permisao para aprovar {}", e1.getMessage());

            
        } catch (Exception e) {
            log.error("Erro ao aprovar solicitação ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Erro ao aprovar");
        }
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void reprovarSolicitacao(Long id) {
        try {
            Solicitacao existente = findById(id);

            // Criar historico doacao
            HistoricoSolicitacao historico = new HistoricoSolicitacao();
            historico.setDataAlteracao(LocalDateTime.now());
            historico.setObservacao("Solicitação reprovada.");
            historico.setExecutor(getNomeUsuarioLogado());
            historico.setStatus(Status.REPROVADO);


            historico.setSolicitacao(existente);
            // Atualizar histórico da solicitação
            existente.getHistorico().add(historico);
            existente.setStatus(Status.REPROVADO);
            solicitacaoRepository.save(existente);
        } catch (Exception e) {
            log.error("Erro ao reprovar solicitação ID {}: {}", id, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Solicitacao selecionarDoacaoSolicitacao(Long solicitacaoId, Long doacaoId)
            throws Exception {
        Solicitacao solicitacao = findById(solicitacaoId);
        Doacao doacaoEscolhida = doacaoRepository.findById(doacaoId)
                .orElseThrow(() -> new Exception("Doação não encontrada com ID: " + doacaoId));
        if (solicitacao == null) {
            throw new Exception("Solicitação não encontrada com ID: " + solicitacaoId);
        }

        if (solicitacao.getStatus() != Status.APROVADO) {
            log.error("Solicitação com ID: {} não está aprovada. Status atual: {}", solicitacaoId,
                    solicitacao.getStatus());
            throw new Exception("Solicitação com ID: " + solicitacaoId + " não está aprovada.");
        }

        if (doacaoEscolhida == null) {
            throw new Exception("Doação não encontrada com ID: " + doacaoId);
        }

        List<Status> statusPermitidos = List.of(Status.APROVADO, Status.APROVADO_IA);
        // Verifica se a doação está disponível para seleção
        if (!statusPermitidos.contains(doacaoEscolhida.getStatus())) {
            log.error("Doação com ID: {} não está disponível para seleção. Status atual: {}", doacaoId,
                    doacaoEscolhida.getStatus());
            throw new Exception("Doação com ID: " + doacaoId + " não está disponível.");
        }

        // --- PARTE DA DOAÇÃO ---
        HistoricoDoacao historicoDoacao = new HistoricoDoacao();
        historicoDoacao.setDataAlteracao(LocalDateTime.now());
        historicoDoacao.setObservacao("Doação vinculada à solicitação ID " + solicitacaoId);
        historicoDoacao.setExecutor(getNomeUsuarioLogado());
        historicoDoacao.setStatus(Status.VINCULADO);

        // IMPORTANTE: Seta a doação para preencher doacao_id no banco
        historicoDoacao.setDoacao(doacaoEscolhida);
        doacaoEscolhida.getHistorico().add(historicoDoacao);
        doacaoEscolhida.setStatus(Status.VINCULADO);

        // --- PARTE DA SOLICITAÇÃO ---
        solicitacao.getDoacoes().add(doacaoEscolhida);

        HistoricoSolicitacao h = new HistoricoSolicitacao();
        h.setDataAlteracao(LocalDateTime.now());
        h.setObservacao("Equipamento ID " + doacaoId + " vinculado com sucesso.");
        h.setStatus(Status.VINCULADO);
        h.setExecutor(getNomeUsuarioLogado());

        // IMPORTANTE: Seta a solicitação para preencher solicitacao_id no banco
        h.setSolicitacao(solicitacao);

        solicitacao.getHistorico().add(h);

        return solicitacaoRepository.save(solicitacao);
    }

    private String getNomeUsuarioLogado() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Pessoa pessoa) {
            return pessoa.getNome(); // Retorna o nome da entidade Pessoa logada
        }
        return "Sistema"; // Fallback para ações automáticas
    }

}
