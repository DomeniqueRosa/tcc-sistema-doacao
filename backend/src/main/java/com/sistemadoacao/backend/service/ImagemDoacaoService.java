package com.sistemadoacao.backend.service;

import org.springframework.stereotype.Service;
import com.sistemadoacao.backend.model.ImagemDoacao;

import com.sistemadoacao.backend.repository.ImagemRepository;

@Service
public class ImagemDoacaoService {

    private final ImagemRepository imagemRepository;

    public ImagemDoacaoService(ImagemRepository img) {
        this.imagemRepository = img;
    }

    public ImagemDoacao listarImagemDoacaoPorId(Long id) {

        if(id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }

        ImagemDoacao imagem = imagemRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
        return imagem;
    }

    public void deleteImagemDoacao(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        imagemRepository.deleteById(id);
    }

}
