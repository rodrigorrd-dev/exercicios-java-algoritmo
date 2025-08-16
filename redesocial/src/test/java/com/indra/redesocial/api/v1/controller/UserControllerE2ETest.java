package com.indra.redesocial.api.v1.controller;

import com.indra.redesocial.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerE2ETest {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void fluxoCompletoUsuario() {
        // 1. Criar usuário
        User user = new User();
        user.setUsername("mariaA12");
        user.setEmail("maris1aa@email.com");
        user.setPasswordHash("algumHa2shSeguro");

        ResponseEntity<User> created = rest.postForEntity("/users", user, User.class);
        assertEquals(HttpStatus.CREATED, created.getStatusCode());

        Long id = created.getBody().getId();

        // 2. Buscar usuário
        User fetched = rest.getForObject("/users/" + id, User.class);
        assertEquals(user.getUsername(), fetched.getUsername());

        // 3. Deletar
        rest.delete("/users/" + id);
    }
}