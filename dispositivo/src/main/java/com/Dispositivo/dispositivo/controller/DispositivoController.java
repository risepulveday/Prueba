package com.Dispositivo.dispositivo.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Dispositivo.dispositivo.model.DispositivoModel;
import com.Dispositivo.dispositivo.service.DispositivoService;
import java.util.List;

@RestController
@RequestMapping("/api/dispositivo")
public class DispositivoController {

    private final DispositivoService dispositivoService;

    DispositivoController(DispositivoService dispositivoService) {
        this.dispositivoService = dispositivoService;
    }

    // 1. Registrar un nuevo dispositivo (Gama media, alta, etc.)
    @PostMapping
    public ResponseEntity<DispositivoModel> registrarDispositivo(@Valid @RequestBody DispositivoModel dispositivo) {
        DispositivoModel nuevo = dispositivoService.registrarDispositivo(dispositivo);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    // 2. Obtener todos los dispositivos registrados en el sistema
    @GetMapping
    public ResponseEntity<List<DispositivoModel>> obtenerTodos() {
        return new ResponseEntity<>(dispositivoService.obtenerTodos(), HttpStatus.OK);
    }

    // 3. Obtener un dispositivo específico por su ID único
    @GetMapping("/{id}")
    public ResponseEntity<DispositivoModel> obtenerPorId(@PathVariable Long id) {
        return dispositivoService.obtenerPorId(id)
                .map(d -> new ResponseEntity<>(d, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 4. Obtener todos los dispositivos asociados a un usuario en específico
    // Útil para cuando el usuario quiera ver si sus equipos corren un juego
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<DispositivoModel>> obtenerPorUsuario(@PathVariable Long idUsuario) {
        List<DispositivoModel> dispositivos = dispositivoService.obtenerPorUsuario(idUsuario);
        return new ResponseEntity<>(dispositivos, HttpStatus.OK);
    }

    // 5. Actualizar los componentes o puntajes de un dispositivo
    @PutMapping("/{id}")
    public ResponseEntity<DispositivoModel> actualizarDispositivo(@PathVariable Long id,
            @Valid @RequestBody DispositivoModel dispositivo) {
        try {
            DispositivoModel actualizado = dispositivoService.actualizarDispositivo(id, dispositivo);
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 6. Eliminar un dispositivo del inventario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDispositivo(@PathVariable Long id) {
        try {
            dispositivoService.eliminarDispositivo(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}