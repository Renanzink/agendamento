package com.arquiteta.agendamento.service;

import com.arquiteta.agendamento.model.Agendamento;
import com.arquiteta.agendamento.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository repository;

    public Agendamento salvarNovoAgendamento(Agendamento agendamento) {
        return repository.save(agendamento);
    }

    public List<Agendamento> listarTudo() {
        return repository.findAll();
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public Agendamento atualizar(Long id, Agendamento dadosNovos) {
        Agendamento existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        existente.setNomeCliente(dadosNovos.getNomeCliente());
        existente.setContato(dadosNovos.getContato());
        existente.setTipoServico(dadosNovos.getTipoServico());
        existente.setDataHora(dadosNovos.getDataHora());

        return repository.save(existente);
    }
}