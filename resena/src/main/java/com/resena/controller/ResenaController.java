package com.Resena.resena.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Resena.resena.model.ResenaModel;
import com.Resena.resena.service.ResenaService;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/resena")
public class ResenaController {
    @Autowired
    private ResenaService resenaService;

    // 1. Crear una nueva resena
    @PostMapping
    public ResponseEntity<ResenaModel> crearResena(@Valid @RequestBody ResenaModel resena) {
        ResenaModel nuevaResena = resenaService.crearResena(resena);
        return new ResponseEntity<>(nuevaResena, HttpStatus.CREATED);
    }

    // 2. Obtener todas las resenas
    @GetMapping
    public ResponseEntity<List<ResenaModel>> obtenerTodas() {
        List<ResenaModel> resenas = resenaService.obtenerTodas();
        return new ResponseEntity<>(resenas, HttpStatus.OK);
    }

    // 3. Obtener una resena por ID
    @GetMapping("/{id}")
    public ResponseEntity<ResenaModel> obtenerPorId(@PathVariable Long id) {
        return resenaService.obtenerPorId(id)
                .map(resena -> new ResponseEntity<>(resena, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 4. Actualizar una resena existente
    @PutMapping("/{id}")
    public ResponseEntity<ResenaModel> actualizarResena(@PathVariable Long id, @Valid @RequestBody ResenaModel resena) {
        try {
            ResenaModel resenaActualizada = resenaService.actualizarResena(id, resena);
            return new ResponseEntity<>(resenaActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 5. Eliminar una resena
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarResena(@PathVariable Long id) {
        try {
            resenaService.eliminarResena(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content es lo ideal para deletes exitosos
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 6. Buscar por fecha: /api/resenas/buscar-fecha?fecha=2026-06-19
    @GetMapping("/buscar-fecha")
    public ResponseEntity<List<ResenaModel>> buscarPorFecha(@RequestParam("fecha") Date fecha) {
        List<ResenaModel> resenas = resenaService.buscarPorFecha(fecha);
        if (resenas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 si no hay resultados
        }
        return new ResponseEntity<>(resenas, HttpStatus.OK);
    }

    // 7. Buscar por total: /api/resenas/buscar-total?total=1500
    @GetMapping("/buscar-total")
    public ResponseEntity<List<ResenaModel>> buscarPorTotal(@RequestParam("total") Integer total) {
        List<ResenaModel> resenas = resenaService.buscarPorTotal(total);
        if (resenas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(resenas, HttpStatus.OK);
    }
}