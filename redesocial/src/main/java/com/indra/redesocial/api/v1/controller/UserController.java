package com.indra.redesocial.api.v1.controller;

import com.indra.redesocial.api.v1.assembler.UserInputDisassembler;
import com.indra.redesocial.api.v1.assembler.UserModelAssembler;
import com.indra.redesocial.api.v1.model.UserModel;
import com.indra.redesocial.api.v1.model.input.UserInput;
import com.indra.redesocial.domain.model.User;
import com.indra.redesocial.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserModelAssembler userModelAssembler;

    @Autowired
    private UserInputDisassembler userInputDisassembler;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel addUser(@RequestBody UserInput userInput) {
        User user = userInputDisassembler.toDomainObject(userInput);
        return userModelAssembler.toModel(userService.saveUser(user));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserModel> researchUsers() {
        return userModelAssembler.toCollectionModel(userService.findAll());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserModel researchUser(@PathVariable("id") Long id) {
        User userAtual = userService.buscarOuFalhar(id);
        return userModelAssembler.toModel(userAtual);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UserModel update(@PathVariable("id") Long id, @RequestBody UserInput userInput) {
        User userAtual = userService.buscarOuFalhar(id);
        userInputDisassembler.copyToDomainObject(userInput, userAtual);
        return userModelAssembler.toModel(userService.update(userAtual));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        try {
            userService.delete(id);
        } catch (Exception e) {
        }
    }
}
