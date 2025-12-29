# CRUD Vuelos ✈️ (Spring Boot - Sin Base de Datos)

Proyecto de práctica en **Spring Boot** para crear una **API REST** de Vuelos (CRUD completo) sin BD (datos en memoria).  
Se trabaja con fechas usando `LocalDate`, validaciones básicas, filtros y ordenamiento.

---

## Qué permite
- Crear vuelos
- Listar vuelos (ordenados por `fechaSalida`)
- Obtener vuelo por ID
- Actualizar vuelo
- Eliminar vuelo
- Filtrar por `empresa`, `lugarLlegada`, `fechaSalida` (combinables)
- Ordenar (bonus) por `empresa` o `lugarLlegada` en `ASC/DESC`
- Devolver `duracionDias` calculado (bonus)

---

## Cómo ejecutar
1) Ejecuta la clase main `VuelosApplication` (la que tiene `@SpringBootApplication`)
2) Prueba la API desde Postman en:
- `http://localhost:8080/vuelos`

---

## Endpoints
### CRUD Básico
- `GET /vuelos` (Todos)
- `GET /vuelos/{id}` (Por ID)
- `POST /vuelos` (Crear)
- `PUT /vuelos/{id}` (Actualizar)
- `DELETE /vuelos/{id}` (Eliminar)
### Filtros
- `GET /vuelos?empresa=Turkish` (Por empresa)
- `GET /vuelos?lugarLlegada=Nueva%20York%20(JFK)` (Por lugar llegada)
- `GET /vuelos?fechaSalida=2026-01-20` (Por fecha salida)
- `GET /vuelos?empresa=...&lugarLlegada=...&fechaSalida=...` (Combinados)
### Ordenamiento
(Por orden ASCENDENTE y DESCENDENTE)
- `GET /vuelos?ordenarPor=empresa&orden=ASC`
- `GET /vuelos?ordenarPor=empresa&orden=DESC`
- `GET /vuelos?ordenarPor=lugarLlegada&orden=ASC`
- `GET /vuelos?ordenarPor=lugarLlegada&orden=DESC`
### Combinado
- `GET /vuelos?empresa=...&lugarLlegada=...&ordenarPor=...&orden=...` (Filtros + Ordenamiento)

---

## Validaciones
- No se permiten campos obligatorios vacíos/nulos (`@Valid`)
- `fechaSalida` no puede ser posterior a `fechaLlegada`

---

## Dataset inicial
Al iniciar la app se cargan **10 vuelos de ejemplo** en memoria para poder probar sin crear todo desde cero.

---

## Estructura del proyecto (simple)
- `src/main/java/com/practica/vuelos/models/Vuelo.java` -> Modelo Vuelo
- `src/main/java/com/practica/vuelos/dtos/VueloRequestDTO.java` -> DTO de entrada (POST/PUT) con validaciones
- `src/main/java/com/practica/vuelos/dtos/VueloResponseDTO.java` -> DTO de salida (GET) con `duracionDias`
- `src/main/java/com/practica/vuelos/utils/FechaUtils.java` -> Cálculo de duración en días
- `src/main/java/com/practica/vuelos/repositories/VuelosRepository.java` -> CRUD en memoria + dataset inicial
- `src/main/java/com/practica/vuelos/services/VuelosService.java` -> Lógica de negocio + filtros + ordenamiento
- `src/main/java/com/practica/vuelos/controllers/VuelosController.java` -> Endpoints REST
- `src/main/java/com/practica/vuelos/exceptions/*` -> Excepciones custom + handler global
- `CRUD Vuelos.postman_collection.json` -> Colección Postman de pruebas

---

Abraham-Ps
