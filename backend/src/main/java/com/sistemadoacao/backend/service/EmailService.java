package com.sistemadoacao.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

// CORRETO
private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmailCadastro(String email, String nome, String senha) {
        
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Cadastro realizado com sucesso!");
            message.setText("Olá " + nome + ",\n\nSeu cadastro no sistema de doações foi realizado com sucesso!\n\nObrigado por se juntar a nós.\n\nAtenciosamente,\nEquipe do Sistema de Doações");
            if(!senha.isBlank()){
                message.setText("Olá \" + nome + \",\\n" + //
                                        "\\n" + //
                                        "Seu cadastro no sistema de doações foi realizado com sucesso!\\n" + //
                                        "\\n" + //
                                        "\\Sua senha temporaria é: "+ senha + //
                                        "\\Obrigado por se juntar a nós.\\n" + //
                                        "\\n" + //
                                        "Atenciosamente,\\n" + //
                                        "Equipe do Sistema de Doaçõessenha temporaria:" + senha);
            }
            mailSender.send(message);



            logger.info("Email enviado com sucesso para " + email);
            
        } catch (MailException e) {
            logger.error("Erro ao enviar email para " + email + ": " + e.getMessage());
        }
    }
}
