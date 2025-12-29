package com.practica.vuelos.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VueloRequestDTO {

    @NotBlank(message = "Nombre del vuelo requerido")
    private String nombreVuelo;
    @NotBlank(message = "Empresa requerida")
    private String empresa;
    @NotBlank(message = "Lugar de salida requerido")
    private String lugarSalida;
    @NotBlank(message = "Lugar de llegada requerido")
    private String lugarLlegada;
    @NotNull(message = "Fecha de salida requerida")
    private LocalDate fechaSalida;
    @NotNull(message = "Fecha de llegada requerida")
    private LocalDate fechaLlegada;
}
