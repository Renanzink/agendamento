package com.arquiteta.agendamento.config;

import com.arquiteta.agendamento.model.Usuario;
import com.arquiteta.agendamento.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // --- TESTE DE EMERGÊNCIA ---
        if ("emylle".equals(username)) {
            System.out.println("DEBUG LOGIN -> Usando usuário virtual de emergência");
            return User.withUsername("emylle")
                    .password("senha123")
                    .authorities("ROLE_ADMIN")
                    .build();
        }
        // ---------------------------

        Usuario usuario = repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        System.out.println("DEBUG LOGIN -> Usuário do Banco: " + usuario.getLogin());

        return User.withUsername(usuario.getLogin())
                .password(usuario.getSenha())
                .authorities("ROLE_ADMIN")
                .build();
    }
}