package com.indra.redesocial.domain;

public class UserNaoEncontrado extends RuntimeException {
    public UserNaoEncontrado(String message) {
        super(message);
    }
}
