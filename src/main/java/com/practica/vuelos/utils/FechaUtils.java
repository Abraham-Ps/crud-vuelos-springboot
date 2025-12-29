package com.practica.vuelos.utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FechaUtils {

    // aqui vamos a calcular la duracion en dias entre 2 fechas
    public static long duracionEnDias(LocalDate desde, LocalDate hasta) {
        // aqui el ChronoUnit.DAYS.between, nos regresa la diferencia en dias
        return ChronoUnit.DAYS.between(desde, hasta);
    }
}
