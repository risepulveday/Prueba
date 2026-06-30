package com.Dispositivo.dispositivo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Dispositivo.dispositivo.model.DispositivoModel;
import com.Dispositivo.dispositivo.service.DispositivoService;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/dispositivo")
public class DispositivoController {
    @Autowired
    private DispositivoService dispositivoService;

    // 1. Crear una nueva dispositivo
    @PostMapping
    public ResponseEntity<DispositivoModel> crearDispositivo(@Valid @RequestBody DispositivoModel dispositivo) {
        DispositivoModel nuevaDispositivo = dispositivoService.crearDispositivo(dispositivo);
        return new ResponseEntity<>(nuevaDispositivo, HttpStatus.CREATED);
    }

    // 2. Obtener todas las dispositivos
    @GetMapping
    public ResponseEntity<List<DispositivoModel>> obtenerTodas() {
        List<DispositivoModel> dispositivos = dispositivoService.obtenerTodas();
        return new ResponseEntity<>(dispositivos, HttpStatus.OK);
    }

    // 3. Obtener una dispositivo por ID
    @GetMapping("/{id}")
    public ResponseEntity<DispositivoModel> obtenerPorId(@PathVariable Long id) {
        return dispositivoService.obtenerPorId(id)
                .map(dispositivo -> new ResponseEntity<>(dispositivo, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 4. Actualizar una dispositivo existente
    @PutMapping("/{id}")
    public ResponseEntity<DispositivoModel> actualizarDispositivo(@PathVariable Long id, @Valid @RequestBody DispositivoModel dispositivo) {
        try {
            DispositivoModel dispositivoActualizada = dispositivoService.actualizarDispositivo(id, dispositivo);
            return new ResponseEntity<>(dispositivoActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 5. Eliminar una dispositivo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDispositivo(@PathVariable Long id) {
        try {
            dispositivoService.eliminarDispositivo(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content es lo ideal para deletes exitosos
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //6. Buscar por fecha: /api/dispositivos/buscar-fecha?fecha=2026-06-19
    @GetMapping("/buscar-fecha")
    public ResponseEntity<List<DispositivoModel>> buscarPorFecha(@RequestParam("fecha") Date fecha) {
        List<DispositivoModel> dispositivos = dispositivoService.buscarPorFecha(fecha);
        if (dispositivos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 si no hay resultados
        }
        return new ResponseEntity<>(dispositivos, HttpStatus.OK);
    }

    //7. Buscar por total: /api/dispositivos/buscar-total?total=1500
    @GetMapping("/buscar-total")
    public ResponseEntity<List<DispositivoModel>> buscarPorTotal(@RequestParam("total") Integer total) {
        List<DispositivoModel> dispositivos = dispositivoService.buscarPorTotal(total);
        if (dispositivos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(dispositivos, HttpStatus.OK);
    }
}