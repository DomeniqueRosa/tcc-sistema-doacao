package com.sistemadoacao.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sistemadoacao.backend.config.Utils;
import com.sistemadoacao.backend.dto.DashboardDTO;
import com.sistemadoacao.backend.dto.GraficoDTO;
import com.sistemadoacao.backend.dto.GraficoEquipamentoDTO;
import com.sistemadoacao.backend.model.Doacao;
import com.sistemadoacao.backend.model.Equipamento;
import com.sistemadoacao.backend.model.HistoricoDoacao;
import com.sistemadoacao.backend.model.Status;
import com.sistemadoacao.backend.repository.DoacaoRepository;
import com.sistemadoacao.backend.repository.UsuarioRepository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DoacaoService {

    private final DoacaoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final Utils utils;

    public DoacaoService(DoacaoRepository repository, UsuarioRepository usuarioRepository, Utils utils) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.utils = utils;
    }

    public Doacao save(@NonNull Doacao novaDoacao) {
        
        // Historico
        HistoricoDoacao histDoacao = new HistoricoDoacao();
        histDoacao.setDataAlteracao(LocalDateTime.now());
        histDoacao.setObservacao("Doação cadastrada em sistema.");
        histDoacao.setExecutor(utils.getNomeUsuarioLogado());
        histDoacao.setStatus(novaDoacao.getStatus());

        histDoacao.setDoacao(novaDoacao);
        novaDoacao.getHistorico().add(histDoacao);
        novaDoacao.setDoadorId(utils.getIdUsuarioLogado());
        return repository.save(novaDoacao);

    }

    public List<Doacao> listarDoacoes() {
        return repository.findAll();
    }

    public List<Doacao> listarDoacoesPorEquipamento(Equipamento e) {
        return repository.findByEquipamento(e);
    }

    public List<Doacao> listarDoacoesPorStatus(Status status) {
        return repository.findByStatus(status);
    }

    public List<Doacao> listarAprovados() {
        return repository.findAprovadas().stream()
                .map(obj -> (Doacao) obj[0])
                .toList();
    }

    public Doacao findByiD(Long id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Doacao nao encontada."));
    }


    public Doacao aprovarDoacao(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID nao pode ser nulo");
        }

        try {
            Doacao doacaoAprovar = findByiD(id);
            // historico
            HistoricoDoacao historicoDoacao = new HistoricoDoacao();
            historicoDoacao.setDataAlteracao(LocalDateTime.now());
            historicoDoacao.setObservacao("Doacao aprovada");
            historicoDoacao.setExecutor(utils.getNomeUsuarioLogado());
            historicoDoacao.setStatus(Status.APROVADO);

            historicoDoacao.setDoacao(doacaoAprovar);

            doacaoAprovar.getHistorico().add(historicoDoacao);

            Doacao doacao = repository.findById(id).orElseThrow();
            doacao.setStatus(Status.APROVADO);
            repository.save(doacao);
            return doacao;
        } catch (Exception e) {
            log.error("Erro ao aprovar doacao");
            return null;
        }
    }

    public Doacao reprovarDoacao(Long id, String motivo) {
        if (id == null) {
            throw new IllegalArgumentException("ID nao pode ser nulo");
        }

        try {

            Doacao doacaoReprovar = findByiD(id);
            // historico
            HistoricoDoacao historicoDoacao = new HistoricoDoacao();
            historicoDoacao.setDataAlteracao(LocalDateTime.now());
            historicoDoacao.setObservacao(motivo);
            historicoDoacao.setExecutor(utils.getNomeUsuarioLogado());
            historicoDoacao.setStatus(Status.REPROVADO);

            historicoDoacao.setDoacao(doacaoReprovar);

            doacaoReprovar.getHistorico().add(historicoDoacao);

            doacaoReprovar.setStatus(Status.REPROVADO);
            repository.save(doacaoReprovar);
            return doacaoReprovar;
        } catch (Exception e) {
            log.error("Erro ao reprovar doacao");
            return null;
        }
    }

    public Long totalDoacoes() {
        return repository.count();
    }

    public Doacao listarDoacaoPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        Doacao doacao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doacao não encontrado com ID: " + id));
        return doacao;
    }

    public boolean deleteDoacao(@NonNull Long id) {
        if (repository.existsById(id) == false) {
            log.warn("Tentativa de deletar doacao não existente: ID {}", id);
            return false;
        } else {
            repository.deleteById(id);
            log.info("Doação deletado com sucesso: ID {}", id);
            return true;
        }
    }

    public Doacao updateDoacao(@NonNull Long id, Doacao atualizado) {

        Doacao existente = listarDoacaoPorId(id);

        if (existente == null) {
            log.error("Erro ao atualizar: Doação não encontrado para atualização: ID {}", id);
            throw new RuntimeException("Erro ao atualizar: Doação não encontrado com ID: " + id);
        }

        if (atualizado.getEquipamento() != null) {
            existente.setEquipamento(atualizado.getEquipamento());
        }

        if (atualizado.getQuantidade() != null) {
            existente.setQuantidade(atualizado.getQuantidade());
        }

        if (atualizado.getDescricao() != null) {
            existente.setDescricao(atualizado.getDescricao());
        }

        if (atualizado.getStatusConservacao() != null) {
            existente.setStatusConservacao(atualizado.getStatusConservacao());
        }

        if (atualizado.getStatus() != null) {
            existente.setStatus(atualizado.getStatus());
        }

        if (atualizado.getImagem() != null) {
            existente.setImagem(atualizado.getImagem());
        }

        return repository.save(existente);
    }

    public DashboardDTO gerarRelatorioGeral() {
        List<Object[]> resultadosBrutosPorEquipamento = repository.findTotalPorEquipamento();

        List<Object[]> resultadosBrutos = repository.findDoacoesMensais();

        // Converte a lista de Object[] para Lista de GraficoDTO
        List<GraficoDTO> grafico = resultadosBrutos.stream()
                .map(p -> new GraficoDTO((Integer) p[0], (Long) p[1]))
                .toList();

        List<GraficoEquipamentoDTO> graficoEquipamento = resultadosBrutosPorEquipamento.stream()
                .map(p -> new GraficoEquipamentoDTO(p[0].toString(), (Long) p[1]))
                .toList();
        return new DashboardDTO(
                usuarioRepository.count(),
                repository.count(),
                repository.countByStatus(Status.DOADO),
                repository.countByStatus(Status.APROVADO),
                repository.countByStatus(Status.APROVADO_IA),
                repository.countByStatus(Status.REPROVADO),
                repository.countByStatus(Status.REPARO),
                grafico,
                graficoEquipamento);
    }

    

}
