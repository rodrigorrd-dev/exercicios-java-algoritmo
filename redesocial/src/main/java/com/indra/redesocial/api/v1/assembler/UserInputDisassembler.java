package com.indra.redesocial.api.v1.assembler;

import com.indra.redesocial.api.v1.model.input.UserInput;
import com.indra.redesocial.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public User toDomainObject(UserInput userInput) {
        return modelMapper.map(userInput, User.class);
    }

    public void copyToDomainObject(UserInput userInput, User user) {
        modelMapper.map(userInput, user);
    }
}
