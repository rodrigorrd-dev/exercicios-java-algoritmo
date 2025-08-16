package com.indra.redesocial.domain.service.user;

import com.indra.redesocial.domain.model.User;
import com.indra.redesocial.domain.repository.UserRepository;
import com.indra.redesocial.domain.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void deveSalvarERecuperarUsuarioNoBanco() {
        User user = new User();
        user.setUsername("joao");
        user.setEmail("joao@email.com");
        user.setPasswordHash("algumHa2shSeguro");

        User salvo = userService.saveUser(user);
        User encontrado = userService.buscarOuFalhar(salvo.getId());

        assertEquals("joao", encontrado.getUsername());
    }
}
