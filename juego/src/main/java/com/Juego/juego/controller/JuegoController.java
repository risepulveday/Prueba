package com.Juego.juego.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Juego.juego.model.JuegoModel;
import com.Juego.juego.service.JuegoService;

import java.util.List;

@RestController
@RequestMapping("/api/juegos")
public class JuegoController {

    private final JuegoService juegoService;

    public JuegoController(JuegoService juegoService) {
        this.juegoService = juegoService;
    }

    @PostMapping
    public ResponseEntity<JuegoModel> crearJuego(@Valid @RequestBody JuegoModel juego) {
        JuegoModel nuevoJuego = juegoService.guardarJuego(juego);
        return new ResponseEntity<>(nuevoJuego, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<JuegoModel>> obtenerTodos() {
        return ResponseEntity.ok(juegoService.obtenerTodos());
    }

    // El Carrito llamará exactamente a este endpoint: /api/juegos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<JuegoModel> obtenerPorId(@PathVariable Long id) {
        return juegoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<JuegoModel> actualizarJuego(@PathVariable Long id, @Valid @RequestBody JuegoModel juego) {
        try {
            return ResponseEntity.ok(juegoService.actualizarJuego(id, juego));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarJuego(@PathVariable Long id) {
        try {
            juegoService.eliminarJuego(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}