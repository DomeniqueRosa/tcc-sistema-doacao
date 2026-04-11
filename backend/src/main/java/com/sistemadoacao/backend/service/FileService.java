package com.sistemadoacao.backend.service;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.tika.Tika;

import com.sistemadoacao.backend.exception.FileStorageException;
import com.sistemadoacao.backend.exception.ImageErroLerException;
import com.sistemadoacao.backend.exception.ImageInvalidException;
import com.sistemadoacao.backend.exception.ImageNullException;
import com.sistemadoacao.backend.exception.MaxUploadSizeException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileService {

    protected final String PASTA = "C:\\tcc-sistema-doacao\\backend\\src\\main\\resources\\static\\images\\";
    private final Tika tika = new Tika();

    private final int tamanhoMaximo = 5 * 1024 * 1024; // 5MB
    // Lista de tipos permitidos (MIME Types)
    private static final List<String> tipos = Arrays.asList(
        "image/jpeg", 
        "image/png", 
        "application/pdf"
    );

    public String salvarArquivo(MultipartFile arquivo) {

        // Validacao imagem
        if(arquivo.isEmpty() || arquivo == null ||  arquivo.getOriginalFilename() == null ) {
            throw new ImageNullException("Arquivo imagem vazio. Por favor, selecione um arquivo para upload.");
        }

        try {
            String tipoDetectado = tika.detect(arquivo.getInputStream());

            if(!tipos.contains(tipoDetectado)) {
                throw new ImageInvalidException("Tipo de arquivo não permitido: " + tipoDetectado);
            }

            if(arquivo.getSize() > tamanhoMaximo) {
                throw new MaxUploadSizeException("O arquivo excede o tamanho máximo permitido de 5MB.");
            }

            log.info("Tipo de arquivo detectado: " + tipoDetectado);
        } catch (IOException e) {
            throw new ImageErroLerException("Erro ao ler o arquivo: " + e.getMessage());
        }

        // Criar o diretório se não existir
        File diretorio = new File(PASTA);

        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        // nome arquivo físico
        String nomeArquivo = System.currentTimeMillis() + "_" + arquivo.getOriginalFilename();
        nomeArquivo = nomeArquivo.replaceAll("\\s+", "_");


        try {
            
            Path caminhoCompleto = Paths.get(PASTA + nomeArquivo);
            Files.write(caminhoCompleto, arquivo.getBytes());

        } catch (Exception e) {
            throw new FileStorageException("Erro ao salvar arquivo.", e.getCause());
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
