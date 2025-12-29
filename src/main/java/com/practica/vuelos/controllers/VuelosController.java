package com.practica.vuelos.controllers;

import com.practica.vuelos.dtos.VueloRequestDTO;
import com.practica.vuelos.dtos.VueloResponseDTO;
import com.practica.vuelos.models.ApiResponse;
import com.practica.vuelos.models.Vuelo;
import com.practica.vuelos.services.VuelosService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/vuelos")
public class VuelosController {
    private final VuelosService service;

    public VuelosController(VuelosService service) {
        this.service = service;
    }

    // aqui obtenemos todos los vueltos con filtros y su orden
    @GetMapping
    public ResponseEntity<ApiResponse<List<VueloResponseDTO>>> listar(@RequestParam(required = false) String empresa,
                                                                      @RequestParam(required = false) String lugarLlegada,
                                                                      @RequestParam(required = false) LocalDate fechaSalida,
                                                                      @RequestParam(required = false) String ordenarPor,
                                                                      @RequestParam(required = false) String orden) {
        List<VueloResponseDTO> vuelos = service.listar(empresa, lugarLlegada, fechaSalida, ordenarPor, orden);
        ApiResponse<List<VueloResponseDTO>> response = new ApiResponse<>(
                true,
                "Lista de vuelos obtenida correctamente✅",
                vuelos
        );
        return ResponseEntity.ok(response);
    }

    // aqui obtenemos un vuelo por id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VueloResponseDTO>> obtenerPorId(@PathVariable int id) {
        VueloResponseDTO dto = service.obtenerPorId(id);
        ApiResponse<VueloResponseDTO> response = new ApiResponse<>(
                true,
                "Vuelo encontrado✅",
                dto
        );
        return ResponseEntity.ok(response);
    }

    // aqui creamos un nuevo vuelo con sus @Valid
    @PostMapping
    public ResponseEntity<ApiResponse<Vuelo>> crear(@Valid @RequestBody VueloRequestDTO dto) {
        Vuelo creado = service.crear(dto);
        ApiResponse<Vuelo> response = new ApiResponse<>(
                true,
                "Vuelo creado exitosamente✅", creado
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // aqui se actualiza un vuelo existente
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VueloResponseDTO>> actualizar(@PathVariable int id, @Valid @RequestBody VueloRequestDTO dto) {
        Vuelo actualizado = service.actualizar(id, dto);
        VueloResponseDTO respuesta = service.obtenerPorId(actualizado.getId());
        ApiResponse<VueloResponseDTO> response = new ApiResponse<>(
                true,
                "Vuelo actualizado correctamente✅",
                respuesta
        );
        return ResponseEntity.ok(response);
    }

    // aqui se elimina un vuelo por id
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable int id) {
        service.eliminar(id);
        ApiResponse<Void> response = new ApiResponse<>(
                true,
                "Vuelo eliminado correctamente✅"
        );
        return ResponseEntity.ok(response);
    }
}