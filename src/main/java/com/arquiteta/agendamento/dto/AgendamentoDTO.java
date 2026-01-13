package com.arquiteta.agendamento.dto;

import lombok.Data;

@Data
public class AgendamentoDTO {
    private String nome;
    private String whatsapp;
    private String servico;
    private String data;
    private String hora;
    public record LoginRequest(String login, String senha) {}

}