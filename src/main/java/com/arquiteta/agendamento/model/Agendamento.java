package com.arquiteta.agendamento.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "agendamentos")
@Data
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID auto-incremento
    private Long id;

    @NotBlank(message = "O nome do cliente não pode estar em branco")
    private String nomeCliente;

    @NotBlank(message = "O contato é obrigatório")
    private String contato;

    @NotNull(message = "Escolha o tipo de serviço")
    private String tipoServico;

    @NotNull(message = "A data e hora são obrigatórias")
    @Future(message = "Você não pode agendar para uma data que já passou")
    private LocalDateTime dataHora;

    private String status = "PENDENTE";

}