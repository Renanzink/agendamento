package com.arquiteta.agendamento.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {

    // Lê os valores do application.properties ou variáveis de ambiente
    @Value("${TWILIO_SID:SEU_SID_PADRAO}")
    private String accountSid;

    @Value("${TWILIO_TOKEN:SEU_TOKEN_PADRAO}")
    private String authToken;

    public void enviarNotificacaoWpp(String nome, String servico, String data) {
        try {
            Twilio.init(accountSid, authToken);

            String texto = String.format(
                    "✨ *Novo Agendamento: Emylle Arq*\n\n" +
                            "👤 *Cliente:* %s\n" +
                            "🛠️ *Serviço:* %s\n" +
                            "📅 *Data:* %s\n\n" +
                            "Acesse o painel para detalhes!", nome, servico, data);

            Message.creator(
                    new com.twilio.type.PhoneNumber("whatsapp:+5573991360438"), // Seu número
                    new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),    // Número do Twilio
                    texto
            ).create();
        } catch (Exception e) {
            System.err.println("Falha ao enviar WhatsApp: " + e.getMessage());
        }
    }
}