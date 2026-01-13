package com.arquiteta.agendamento.repository;

import com.arquiteta.agendamento.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    boolean existsByDataHoraAndStatusNot(LocalDateTime dataHora, String status);

    List<Agendamento> findByStatus(String status);
}