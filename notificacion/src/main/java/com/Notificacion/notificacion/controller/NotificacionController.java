package com.Notificacion.notificacion.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Notificacion.notificacion.model.NotificacionModel;
import com.Notificacion.notificacion.service.NotificacionService;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/notificacion")
public class NotificacionController {
    @Autowired
    private NotificacionService notificacionService;

    // 1. Crear una nueva notificacion
    @PostMapping
    public ResponseEntity<NotificacionModel> crearNotificacion(@Valid @RequestBody NotificacionModel notificacion) {
        NotificacionModel nuevaNotificacion = notificacionService.crearNotificacion(notificacion);
        return new ResponseEntity<>(nuevaNotificacion, HttpStatus.CREATED);
    }

    // 2. Obtener todas las notificacions
    @GetMapping
    public ResponseEntity<List<NotificacionModel>> obtenerTodas() {
        List<NotificacionModel> notificacions = notificacionService.obtenerTodas();
        return new ResponseEntity<>(notificacions, HttpStatus.OK);
    }

    // 3. Obtener una notificacion por ID
    @GetMapping("/{id}")
    public ResponseEntity<NotificacionModel> obtenerPorId(@PathVariable Long id) {
        return notificacionService.obtenerPorId(id)
                .map(notificacion -> new ResponseEntity<>(notificacion, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 4. Actualizar una notificacion existente
    @PutMapping("/{id}")
    public ResponseEntity<NotificacionModel> actualizarNotificacion(@PathVariable Long id,
            @Valid @RequestBody NotificacionModel notificacion) {
        try {
            NotificacionModel notificacionActualizada = notificacionService.actualizarNotificacion(id, notificacion);
            return new ResponseEntity<>(notificacionActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 5. Eliminar una notificacion
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNotificacion(@PathVariable Long id) {
        try {
            notificacionService.eliminarNotificacion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content es lo ideal para deletes exitosos
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 6. Buscar por fecha: /api/notificacions/buscar-fecha?fecha=2026-06-19
    @GetMapping("/buscar-fecha")
    public ResponseEntity<List<NotificacionModel>> buscarPorFecha(@RequestParam("fecha") Date fecha) {
        List<NotificacionModel> notificacions = notificacionService.buscarPorFecha(fecha);
        if (notificacions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 si no hay resultados
        }
        return new ResponseEntity<>(notificacions, HttpStatus.OK);
    }

    // 7. Buscar por usuario ID: /api/notificacions/buscar-usuario?idUsuario=1
    @GetMapping("/buscar-usuario")
    public ResponseEntity<List<NotificacionModel>> buscarPorUsuario(@RequestParam("idUsuario") Long idUsuario) {
        List<NotificacionModel> notificacions = notificacionService.buscarPorUsuario(idUsuario);
        if (notificacions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(notificacions, HttpStatus.OK);
    }
}