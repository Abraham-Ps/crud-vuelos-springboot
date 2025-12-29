package com.practica.vuelos.repositories;

import com.practica.vuelos.models.Vuelo;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class VuelosRepository {

    // aqui guardariamos los vuelos en memory, usando int como clave (id)
    private final Map<Integer, Vuelo> data = new HashMap<>();

    // aqui llevamos el prox id autoincrement
    private int nextId = 1;

    // aqui con el postcontruct lo que hacemos es hacer un preload, para que se ejecuta de una vez con un memory de los 10 vuelos, asi no los tengo q crear a mano cada vez q inicie
    // aqui reutilice la lista de vuelos de mi anterior proyecto de Vuelos y modifique algunas cosas para probar el metodo duracionDias
    // y tambien en toda la lista se le cambia el id a todos por 0, por el metodo de autoincrement q implementamos
    @PostConstruct
    public void init() {
        List<Vuelo> vuelosIniciales = List.of(
                // Vuelo corto (1 dia)
                new Vuelo(0, "VL101", "LATAM Airlines", "Santiago De Chile (SCL)", "Madrid (MAD)", LocalDate.of(2026, 1, 15), LocalDate.of(2026, 1, 16)),

                // Vuelo mismo día (0 dias)
                new Vuelo(0, "VL102", "American Airlines", "Miami (MIA)", "Nueva York (JFK)", LocalDate.of(2026, 1, 20), LocalDate.of(2026, 1, 20)),

                // Vuelo 3 dias
                new Vuelo(0, "VL103", "Iberia", "Madrid (MAD)", "Buenos Aires (EZE)", LocalDate.of(2026, 1, 25), LocalDate.of(2026, 1, 28)),

                // Vuelo 5 dias
                new Vuelo(0, "VL104", "Avianca", "Bogota (BOG)", "Ciudad de Mexico (MEX)", LocalDate.of(2026, 2, 1), LocalDate.of(2026, 2, 6)),

                // Vuelo 1 semana (7 dias)
                new Vuelo(0, "VL105", "Delta", "Atlanta (ATL)", "Roma (FCO)", LocalDate.of(2026, 2, 10), LocalDate.of(2026, 2, 17)),

                // Vuelo 2 dias
                new Vuelo(0, "VL106", "Emirates", "Dubai (DXB)", "Nueva York (JFK)", LocalDate.of(2026, 2, 20), LocalDate.of(2026, 2, 22)),

                // Vuelo mismo día (0 dias)
                new Vuelo(0,"VL107", "ITA Airways", "Roma (FCO)", "Paris (CDG)", LocalDate.of(2026, 2, 25), LocalDate.of(2026, 2, 25)),

                // Vuelo 10 dias
                new Vuelo(0, "VL108", "Virgin Atlantic", "Londres (LHR)", "Miami (MIA)", LocalDate.of(2026, 3, 1), LocalDate.of(2026, 3, 11)),

                // Vuelo 4 dias
                new Vuelo(0, "VL109", "Turkish Airlines", "Estambul (IST)", "Caracas (CCS)", LocalDate.of(2026, 3, 15), LocalDate.of(2026, 3, 19)),

                // Vuelo 2 semanas (14 dias)
                new Vuelo(0, "VL110", "Copa Airlines", "Ciudad de Panama (PTY)", "Lima (LIM)", LocalDate.of(2026, 3, 20), LocalDate.of(2026, 4, 3))
        );
        vuelosIniciales.forEach(this::save);
    }
    // aqui guardamos el vuelo nuevos o un update si ya tiene un id
    public Vuelo save(Vuelo vuelo) {
        //aqui si no tiene id, se le asigna un autoincrement
        if (vuelo.getId() == 0) {
            vuelo.setId(nextId++);
        }
        data.put(vuelo.getId(), vuelo);
        return vuelo;
    }

    // aqui devuelvo todos los vuelos ordenas por fecha de salida
    public List<Vuelo> findAll() {
        //aqui hago un sorted para ordenar las fechas de salida por el mas antiguo primero
        return data.values().stream()
                .sorted((v1, v2) -> v1.getFechaSalida().compareTo(v2.getFechaSalida()))
                .toList();
    }

    // aqui estaria la busqueda de vuelo por id
    public Optional<Vuelo> findById(Integer id) {
        return Optional.ofNullable(data.get(id));
    }

    // aqui actualizamos un vuelo existente
    public Vuelo update(int id, Vuelo vuelo) {
        vuelo.setId(id);
        data.put(id, vuelo);
        return vuelo;
    }

    // aqui estaria la eliminacion de un vuelo por id
    public void delete(int id) {
        data.remove(id);
    }
}