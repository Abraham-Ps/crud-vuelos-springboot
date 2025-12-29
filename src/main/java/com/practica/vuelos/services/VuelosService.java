package com.practica.vuelos.services;

import com.practica.vuelos.dtos.VueloRequestDTO;
import com.practica.vuelos.dtos.VueloResponseDTO;
import com.practica.vuelos.exceptions.FechaInvalidaException;
import com.practica.vuelos.exceptions.VueloExistenteException;
import com.practica.vuelos.exceptions.VueloNotFoundException;
import com.practica.vuelos.models.Vuelo;
import com.practica.vuelos.repositories.VuelosRepository;
import com.practica.vuelos.utils.FechaUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class VuelosService {
    private final VuelosRepository repository;
    public VuelosService(VuelosRepository repository) {
        this.repository = repository;
    }

    // aqui se crea un vuelo nuevo (validaciones de null las hace @Valid en el controller)
    public Vuelo crear(VueloRequestDTO dto) {
        // aqui se valida las cosas que @Valid no me cubre
        if (dto.getFechaSalida().isAfter(dto.getFechaLlegada())) {
            throw new FechaInvalidaException("La fecha de salida no puede ser posterior a la de llegada");
        }

        // aqui evitamos duplicados por nombreVuelo
        boolean existe = repository.findAll().stream()
                .anyMatch(v -> v.getNombreVuelo().equalsIgnoreCase(dto.getNombreVuelo()));
        if (existe) {
            throw new VueloExistenteException("Ya existe un vuelo con el mismo nombre");
        }

        //aqui hacemos un map del DTO a model y hacemos un save
        return repository.save(mapearDesdeDto(dto));
    }

    // aqui obtenemos un vuelo por id y lo pasamos a DTO con su duracion
    public VueloResponseDTO obtenerPorId(int id) {
        Vuelo vuelo = repository.findById(id)
                .orElseThrow(() -> new VueloNotFoundException("Vuelo no encontrado"));
        return mapearADto(vuelo);
    }

    // aqui actualizamos un vuelo existente
    public Vuelo actualizar(int id, VueloRequestDTO dto) {
        // aqui validamos q exista
        Vuelo existente = repository.findById(id)
                .orElseThrow(() -> new VueloNotFoundException("Vuelo no encontrado"));
        // aqui se validan fechas si vienen
        if (dto.getFechaSalida() != null && dto.getFechaLlegada() != null &&
                dto.getFechaSalida().isAfter(dto.getFechaLlegada())) {
            throw new FechaInvalidaException("La fecha de salida no puede ser posterior a la de llegada");
        }

        // aqui se actualizan solo los campos que NO sean nulos
        ///* DATO: aqui puedo usar otro metodo que es usando el if, aqui uso el metodo con Optionals pero en mi opinion
        /// se ve mejor y en temas de funcionalidad nos evitamos explicits if, de todas formas con el if seria
        ///  if (dto.getNombreVuelo()!= null) existente.setNombreVuelo(dto.getNombreVuelo());

        Optional.ofNullable(dto.getNombreVuelo()).ifPresent(existente::setNombreVuelo);
        Optional.ofNullable(dto.getEmpresa()).ifPresent(existente::setEmpresa);
        Optional.ofNullable(dto.getLugarSalida()).ifPresent(existente::setLugarSalida);
        Optional.ofNullable(dto.getLugarLlegada()).ifPresent(existente::setLugarLlegada);
        Optional.ofNullable(dto.getFechaSalida()).ifPresent(existente::setFechaSalida);
        Optional.ofNullable(dto.getFechaLlegada()).ifPresent(existente::setFechaLlegada);
        return repository.update(id, existente);
    }

    //aqui eliminamos un vuelo por id
    public void eliminar(int id) {
        repository.findById(id)
                .orElseThrow(() -> new VueloNotFoundException("Vuelo no encontrado"));
        repository.delete(id);
    }

    // aqui se lista con sus filtros y orden
    public List<VueloResponseDTO> listar(String empresa, String lugarLlegada, LocalDate fechaSalida,
                                         String ordenarPor, String orden) {

        // aqui empezamos con todos los vuelos ordenados por fechaSalida
        List<Vuelo> vuelos = repository.findAll();

        // aqui aplicamos filtros con streams si vienen con parametros
        List<Vuelo> filtrados = vuelos.stream()
                .filter(v -> empresa == null || v.getEmpresa().equalsIgnoreCase(empresa))
                .filter(v -> lugarLlegada == null || v.getLugarLlegada().equalsIgnoreCase(lugarLlegada))
                .filter(v -> fechaSalida == null || v.getFechaSalida().equals(fechaSalida))
                .toList();

        // aqui aplicamos ordenamiento extra si viene con ordenarPor
        Comparator<Vuelo> comparator = getVueloComparator(ordenarPor, orden);

        // aqui ordenamos y mapeamos a DTO con duracion
        return filtrados.stream()
                .sorted(comparator)
                .map(this::mapearADto)
                .toList();
    }

    // aqui obtenemos el comparator segun el campo y orden (ASC/DESC)
    private static Comparator<Vuelo> getVueloComparator(String ordenarPor, String orden) {
        // aqui si ordenarPor es null, usamos fechaSalida por defecto
        if (ordenarPor == null) {
            ordenarPor = "";
        }

        Comparator<Vuelo> comparator = switch (ordenarPor) {
            case "empresa" -> Comparator.comparing(Vuelo::getEmpresa);
            case "lugarLlegada" -> Comparator.comparing(Vuelo::getLugarLlegada);
            default -> Comparator.comparing(Vuelo::getFechaSalida);
        };

        if ("DESC".equalsIgnoreCase(orden)) {
            comparator = comparator.reversed();
        }
        return comparator;
    }

    // aqui mapea desde DTO a model, se puede reutilizar
    private Vuelo mapearDesdeDto(VueloRequestDTO dto) {
        Vuelo vuelo = new Vuelo();
        vuelo.setNombreVuelo(dto.getNombreVuelo());
        vuelo.setEmpresa(dto.getEmpresa());
        vuelo.setLugarSalida(dto.getLugarSalida());
        vuelo.setLugarLlegada(dto.getLugarLlegada());
        vuelo.setFechaSalida(dto.getFechaSalida());
        vuelo.setFechaLlegada(dto.getFechaLlegada());
        return vuelo;
    }
    // aqui mapea desde model a DTO con duracion, se puede reutilizar tambien
    private VueloResponseDTO mapearADto(Vuelo vuelo) {
        VueloResponseDTO dto = new VueloResponseDTO();
        dto.setIdVuelo(vuelo.getId());
        dto.setNombreVuelo(vuelo.getNombreVuelo());
        dto.setEmpresa(vuelo.getEmpresa());
        dto.setLugarSalida(vuelo.getLugarSalida());
        dto.setLugarLlegada(vuelo.getLugarLlegada());
        dto.setFechaSalida(vuelo.getFechaSalida());
        dto.setFechaLlegada(vuelo.getFechaLlegada());
        dto.setDuracionDias(FechaUtils.duracionEnDias(vuelo.getFechaSalida(), vuelo.getFechaLlegada()));
        return dto;
    }
}