package com.indra.redesocial.domain.service;

import com.indra.redesocial.domain.UserNaoEncontrado;
import com.indra.redesocial.domain.model.User;
import com.indra.redesocial.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User buscarOuFalhar(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new UserNaoEncontrado("Usuario Nao econtrado"));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Hard delete
    @Transactional
    public void delete(Long userId) {
        User userAtual = buscarOuFalhar(userId);
        userRepository.delete(userAtual);
    }

    @Transactional
    public User update(User user) {
        return userRepository.save(user);
    }

    //Soft delete
//    @Transactional
//    public void enable(Long userId) {
//        User userAtual = buscarOuFalhar(userId);
//        userAtual.ativar();
//    }
//
//    @Transactional
//    public void disable(Long userId) {
//        User userAtual = buscarOuFalhar(userId);
//        userAtual.inativar();
//    }



}
