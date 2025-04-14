package com.motogo.backend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String remetente;

    public void enviarEmailBoasVindas(String para, String nomeCliente) {
        try {
            Context context = new Context();
            context.setVariable("nome", nomeCliente);

            String corpoHtml = templateEngine.process("emails/boasVindas", context);

            MimeMessage mensagem = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "UTF-8");

            helper.setTo(para);
            helper.setSubject("Bem-vindo à Motogo");
            helper.setFrom(remetente);
            helper.setText(corpoHtml, true);

            mailSender.send(mensagem);

        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail HTML: " + e.getMessage(), e);
        }
    }
}
