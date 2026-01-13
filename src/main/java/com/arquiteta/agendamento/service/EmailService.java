package com.arquiteta.agendamento.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarNotificacaoNovoAgendamento(String nomeCliente, String servico, String dataHora) {
        SimpleMailMessage mensagem = new SimpleMailMessage();

        mensagem.setFrom("contato.emyllearq@gmail.com");
        mensagem.setTo("contato.emyllearq@gmail.com");
        mensagem.setSubject("✨ Novo Agendamento - Emylle Arq");

        // Corpo do e-mail limpo e formatado
        String corpo = String.format(
                "Olá, Emylle!\n\n" +
                        "Você recebeu um novo agendamento pelo site:\n\n" +
                        "👤 Cliente: %s\n" +
                        "🛠️ Serviço: %s\n" +
                        "📅 Data/Hora: %s\n\n" +
                        "--- \n" +
                        "Este é um aviso automático do seu sistema de gestão.",
                nomeCliente, servico, dataHora
        );

        mensagem.setText(corpo);
        mailSender.send(mensagem);
    }
}