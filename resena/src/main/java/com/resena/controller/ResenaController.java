package com.resena.controller;

import com.resena.model.ResenaModel;
import com.resena.service.ResenaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/resenas") // Ruta base para los endpoints de reseñas
public class ResenaController {

    private final ResenaService resenaService;

    // Inyección por Constructor (Evita alertas en VS Code)
    public ResenaController(ResenaService resenaService) {
        this.resenaService = resenaService;
    }

    // 1. Crear una nueva reseña
    @PostMapping
    public ResponseEntity<ResenaModel> crearResena(@RequestBody ResenaModel resena) {
        ResenaModel nuevaResena = resenaService.crearResena(resena);
        return new ResponseEntity<>(nuevaResena, HttpStatus.CREATED);
    }

    // 2. Obtener todas las reseñas
    @SuppressWarnings("unused")
    @GetMapping
    public ResponseEntity<List<ResenaModel>> obtenerTodas() {
        List<ResenaModel> resenas = resenaService.obtenerTodas();
        return ResponseEntity.ok(resenas);
    }

    // 3. Obtener una reseña por su ID
    @GetMapping("/{id}")
    public ResponseEntity<ResenaModel> obtenerPorId(@PathVariable Long id) {
        return resenaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Actualizar una reseña existente
    @PutMapping("/{id}")
    public ResponseEntity<ResenaModel> actualizarResena(@PathVariable Long id,
            @RequestBody ResenaModel resenaDetalles) {
        try {
            ResenaModel resenaActualizada = resenaService.actualizarResena(id, resenaDetalles);
            return ResponseEntity.ok(resenaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. Eliminar una reseña
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarResena(@PathVariable Long id) {
        try {
            resenaService.eliminarResena(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 6. Buscar reseñas por una fecha específica
    @GetMapping("/buscar/fecha")
    public ResponseEntity<List<ResenaModel>> buscarPorFecha(@RequestParam Date fecha) {
        List<ResenaModel> resenas = resenaService.buscarPorFecha(fecha);
        return ResponseEntity.ok(resenas);
    }

    // 7. Buscar reseñas por calificación (estrellas)
    @GetMapping("/buscar/calificacion")
    public ResponseEntity<List<ResenaModel>> buscarPorCalificacion(@RequestParam Integer calificacion) {
        List<ResenaModel> resenas = resenaService.buscarPorCalificacion(calificacion);
        return ResponseEntity.ok(resenas);
    }

    // 8. Buscar todas las reseñas de un usuario en particular
    @GetMapping("/buscar/usuario/{idUsuario}")
    public ResponseEntity<List<ResenaModel>> buscarPorUsuario(@PathVariable Long idUsuario) {
        List<ResenaModel> resenas = resenaService.buscarPorUsuario(idUsuario);
        return ResponseEntity.ok(resenas);
    }

    // 9. Obtener una reseña enriquecida con los datos completos de su usuario
    @GetMapping("/{id}/completa")
    public ResponseEntity<com.resena.dto.ResenaConUsuarioDTO> obtenerResenaCompleta(@PathVariable Long id) {
        try {
            com.resena.dto.ResenaConUsuarioDTO resenaCompleta = resenaService.obtenerResenaConSuUsuario(id);
            return ResponseEntity.ok(resenaCompleta);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}