package com.sistemadoacao.backend.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileService {

    protected final String PASTA = "C:\\tcc-sistema-doacao\\backend\\src\\main\\resources\\static\\images\\";

    public String salvarArquivo(MultipartFile arquivo) throws IOException {

        // Criar o diretório se não existir
        File diretorio = new File(PASTA);

        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
        // nome arquivo físico
        String nomeArquivo = System.currentTimeMillis() + "_" + arquivo.getOriginalFilename();
        nomeArquivo = nomeArquivo.replaceAll("\\s+", "_");

        // Validar se é imagem
        BufferedImage bi = ImageIO.read(arquivo.getInputStream());
        if (bi == null) {
            throw new IllegalArgumentException();
        }

        if (arquivo.isEmpty()) {
            throw new RuntimeErrorException(null, "Arquivo vazio.");
        }

        try {
            
            Path caminhoCompleto = Paths.get(PASTA + nomeArquivo);
            Files.write(caminhoCompleto, arquivo.getBytes());

        } catch (Exception e) {
            throw new RuntimeErrorException((Error) e.getCause(), "Erro ao salvar arquivo.");
        }

        return nomeArquivo;
    }

    public void deletarArquivo(String nomeArquivo){
        try {
                Path caminhoImagem = Paths.get(PASTA + nomeArquivo);
                Files.deleteIfExists(caminhoImagem);
            } catch (IOException e) {
                log.error("Erro ao deletar imagem: " + e.getMessage());
            }
    }

}
