package com.sistemadoacao.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sistemadoacao.backend.dto.SolicitacaoRequest;
import com.sistemadoacao.backend.model.Solicitacao;
import com.sistemadoacao.backend.model.Status;
import com.sistemadoacao.backend.repository.SolicitacaoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;



    public SolicitacaoService(SolicitacaoRepository solicitacaoRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
    }

    public Solicitacao save(SolicitacaoRequest solicitacao, Long usuarioId) {

        // Lógica de negócio: Solicitação sempre inicia com status PENDENTE
        Solicitacao solicitacaoEntity = new Solicitacao();
        solicitacaoEntity.setUsuarioId(usuarioId);
        solicitacaoEntity.setCurso(solicitacao.curso());
        solicitacaoEntity.setGrr(solicitacao.grr());
        solicitacaoEntity.setMotivo(solicitacao.motivo());
        solicitacaoEntity.setSem_computador(solicitacao.semComputador());
        solicitacaoEntity.setAtivo(solicitacao.ativo());
        solicitacaoEntity.setStatus(Status.PENDENTE);


        log.info("Salvando solicitação para usuário ID: {}", usuarioId);
        log.debug("Detalhes da solicitação: {}", solicitacaoEntity);
        Solicitacao nova = solicitacaoRepository.save(solicitacaoEntity);
       
        return nova;
    }

    public List<Solicitacao> findByUsuarioId(Long id) {
        return solicitacaoRepository.findAllByUsuarioId(id);
    }

    public List<Solicitacao> findAll() {
        return solicitacaoRepository.findAll();
    }

}
