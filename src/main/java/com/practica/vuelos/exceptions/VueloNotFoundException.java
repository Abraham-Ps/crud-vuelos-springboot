package com.practica.vuelos.exceptions;

public class VueloNotFoundException extends RuntimeException {
    public VueloNotFoundException(String message) {
        super(message);
    }
}
