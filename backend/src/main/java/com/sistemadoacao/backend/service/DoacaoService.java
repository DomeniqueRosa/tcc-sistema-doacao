package com.sistemadoacao.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sistemadoacao.backend.model.Doacao;
import com.sistemadoacao.backend.repository.DoacaoRepository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DoacaoService {

    private final DoacaoRepository repository;

    public DoacaoService(DoacaoRepository repository) {
        this.repository = repository;
    }

    public Doacao save(@NonNull Doacao novaDoacao) {
        return repository.save(novaDoacao);

    }

    public List<Doacao> listarDoacoes() {
        return repository.findAll();
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
        if(repository.existsById(id) == false) {
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

        if(existente == null) {
            log.error("Erro ao atualizar: Doação não encontrado para atualização: ID {}", id);
            throw new RuntimeException("Erro ao atualizar: Doação não encontrado com ID: " + id);
        }

        if(atualizado.getCpfUsuario() != null){
            existente.setCpfUsuario(atualizado.getCpfUsuario());
        }

        if(atualizado.getEquipamento() != null){
            existente.setEquipamento(atualizado.getEquipamento());
        }

        if(atualizado.getQuantidade() != null){
            existente.setQuantidade(atualizado.getQuantidade());
        }

        if(atualizado.getDescricao() != null){
            existente.setDescricao(atualizado.getDescricao());
        }

        if(atualizado.getStatusConservacao() != null){
            existente.setStatusConservacao(atualizado.getStatusConservacao());
        }

        if(atualizado.getStatus() != null){
            existente.setStatus(atualizado.getStatus());
        }

        if(atualizado.getImagem() != null){
            existente.setImagem(atualizado.getImagem());
        }

        

        return repository.save(existente);


    
        
    }

}
