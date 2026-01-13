package com.arquiteta.agendamento.controller;

import com.arquiteta.agendamento.model.Agendamento;
import com.arquiteta.agendamento.repository.AgendamentoRepository;
import com.arquiteta.agendamento.service.AgendamentoService;
import com.arquiteta.agendamento.service.EmailService;
import com.arquiteta.agendamento.service.WhatsAppService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/agendamentos")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class AgendamentoController {

    @Autowired
    private AgendamentoService service;

    @Autowired
    private AgendamentoRepository repository;

    @Autowired
    private WhatsAppService whatsAppService;

    @Autowired
    private EmailService emailService;


    @GetMapping
    public ResponseEntity<List<Agendamento>> listarTodos() {
        List<Agendamento> lista = service.listarTudo();
        return ResponseEntity.ok(lista);
    }


    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody Agendamento agendamento) {
        // Verificação de Disponibilidade
        boolean horarioOcupado = repository.existsByDataHoraAndStatusNot(agendamento.getDataHora(), "CANCELADO");

        if (horarioOcupado) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Este horário já está reservado. Por favor, escolha outro momento."));
        }


        if (agendamento.getStatus() == null) {
            agendamento.setStatus("PENDENTE");
        }

        Agendamento salvo = service.salvarNovoAgendamento(agendamento);


        try {
            emailService.enviarNotificacaoNovoAgendamento(
                    salvo.getNomeCliente(),
                    salvo.getTipoServico(),
                    salvo.getDataHora().toString()
            );
        } catch (Exception e) {
            System.err.println("Erro e-mail: " + e.getMessage());
        }

        try {
            whatsAppService.enviarNotificacaoWpp(
                    salvo.getNomeCliente(),
                    salvo.getTipoServico(),
                    salvo.getDataHora().toString()
            );
            System.out.println("WhatsApp enviado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro WhatsApp: " + e.getMessage());
        }

        return ResponseEntity.ok(salvo);
    }


    @PatchMapping("/{id}/concluir")
    public ResponseEntity<?> concluir(@PathVariable Long id) {
        return repository.findById(id).map(agendamento -> {
            agendamento.setStatus("CONCLUIDO");
            repository.save(agendamento);
            return ResponseEntity.ok(Map.of("message", "Agendamento concluído com sucesso!"));
        }).orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Agendamento> atualizar(@PathVariable Long id, @Valid @RequestBody Agendamento dados) {
        Agendamento atualizado = service.atualizar(id, dados);
        return ResponseEntity.ok(atualizado);
    }
}