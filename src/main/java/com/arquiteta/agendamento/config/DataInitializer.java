package com.arquiteta.agendamento.config;

import com.arquiteta.agendamento.model.Usuario;
import com.arquiteta.agendamento.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository repository) {
        return args -> {
            if (repository.findByLogin("emylle").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setLogin("emylle");
                admin.setSenha("senha123");
                repository.save(admin);
                System.out.println("LOG -> Usuário admin criado com sucesso!");
            }
        };
    }
}