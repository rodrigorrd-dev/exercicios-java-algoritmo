package com.indra.redesocial.domain.service.user;

import com.indra.redesocial.domain.UserNaoEncontrado;
import com.indra.redesocial.domain.model.User;
import com.indra.redesocial.domain.repository.UserRepository;
import com.indra.redesocial.domain.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setup() {
        user = new User();
        user.setId(1L);
        user.setUsername("rodrigo");
        user.setEmail("rodrigo@email.com");
    }

    @Test
    void buscarOuFalhar() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User resultado = userService.buscarOuFalhar(1L);

        assertNotNull(resultado);
        assertEquals("rodrigo", resultado.getUsername());
        verify(userRepository).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNaoEncontrado.class, () -> userService.buscarOuFalhar(99L));

        verify(userRepository).findById(99L);
    }

    @Test
    void findAll() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<User> usuarios = userService.findAll();

        assertEquals(1, usuarios.size());
        assertEquals("rodrigo", usuarios.get(0).getUsername());
        verify(userRepository).findAll();
    }

    @Test
    void saveUser() {
        when(userRepository.save(user)).thenReturn(user);

        User salvo = userService.saveUser(user);

        assertNotNull(salvo);
        assertEquals("rodrigo", salvo.getUsername());
        verify(userRepository).save(user);
    }

    @Test
    void delete() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.delete(1L);

        verify(userRepository).delete(user);
    }

    @Test
    void deveLancarExcecaoAoDeletarUsuarioInexistente() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNaoEncontrado.class, () -> userService.delete(1L));

        verify(userRepository, never()).delete(any());
    }

    @Test
    void update() {
        when(userRepository.save(user)).thenReturn(user);

        User atualizado = userService.update(user);

        assertNotNull(atualizado);
        assertEquals("rodrigo", atualizado.getUsername());
        verify(userRepository).save(user);
    }
}
