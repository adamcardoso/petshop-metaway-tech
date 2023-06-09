package com.metaway.petshop.exceptions;

public class EntityNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(String msg) {
        super(msg != null ? msg : "Entity not found.");
    }
}
