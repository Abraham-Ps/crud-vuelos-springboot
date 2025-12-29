package com.practica.vuelos.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {

    // aqui coge los errores de validacion (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errores);
    }

    // aqui captura cuando no se encuentra un vuelo
    @ExceptionHandler(VueloNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(VueloNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
    }

    // aqui coge los datos invalidos
    @ExceptionHandler(DatosInvalidosException.class)
    public ResponseEntity<Map<String, String>> handleDatosInvalidos(DatosInvalidosException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }

    // aqui agarra las fechas invalidas
    @ExceptionHandler(FechaInvalidaException.class)
    public ResponseEntity<Map<String, String>> handleFechaInvalida(FechaInvalidaException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }

    // aqui coge cualquier otro runtime exception
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntime(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", ex.getMessage()));
    }

    // aqui captura cuando ya existe un vuelo duplicado
    @ExceptionHandler(VueloExistenteException.class)
    public ResponseEntity<Map<String, String>> handleVueloExistente(VueloExistenteException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", ex.getMessage()));
    }
}