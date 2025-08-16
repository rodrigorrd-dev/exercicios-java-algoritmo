package com.indra.redesocial.api.v1.assembler;

import com.indra.redesocial.api.v1.model.UserModel;
import com.indra.redesocial.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public UserModel toModel(User User) {
        return modelMapper.map(User, UserModel.class);
    }

    public UserModel toModelResumo(User User) {
        return modelMapper.map(User, UserModel.class);
    }

    public List<UserModel> toCollectionModel(Collection<User> blocos) {
        return blocos.stream()
                .map(User -> toModel(User))
                .collect(Collectors.toList());
    }
}
