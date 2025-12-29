package com.practica.vuelos.models;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class ApiResponse<T> {
    private boolean success;
    private String message;
    // aqui guardaremos los datos (Vuelo, VueloResponseDTO, List<VueloResponseDTO>, etc.)
    private T data;
    private LocalDateTime timestamp;

    // aqui constructor para respuestas con datos
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    // aqui constructor para respuestas sin datos, como es DELETE
    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.data = null;
        this.timestamp = LocalDateTime.now();
    }
}