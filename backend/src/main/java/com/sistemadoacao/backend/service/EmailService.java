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

    public void enviarEmailSimples(String para, String assunto, String texto) {
        
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(para);
            message.setSubject(assunto);
            message.setText(texto);
            mailSender.send(message);

            logger.info("Email enviado com sucesso para " + para);
            
        } catch (MailException e) {
            logger.error("Erro ao enviar email para " + para + ": " + e.getMessage());
        }
    }
}
