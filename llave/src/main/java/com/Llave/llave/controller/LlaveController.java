package com.Llave.llave.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Llave.llave.model.LlaveModel;
import com.Llave.llave.service.LlaveService;

import java.util.List;

@RestController
@RequestMapping("/api/llave")
public class LlaveController {
    @Autowired
    private LlaveService llaveService;

    // 1. Crear una nueva llave
    @PostMapping
    public ResponseEntity<LlaveModel> crearLlave(@Valid @RequestBody LlaveModel llave) {
        LlaveModel nuevaLlave = llaveService.crearLlave(llave);
        return new ResponseEntity<>(nuevaLlave, HttpStatus.CREATED);
    }

    // 2. Obtener todas las llaves
    @GetMapping
    public ResponseEntity<List<LlaveModel>> obtenerTodas() {
        List<LlaveModel> llaves = llaveService.obtenerTodas();
        return new ResponseEntity<>(llaves, HttpStatus.OK);
    }

    // 3. Obtener una llave por ID
    @GetMapping("/{id}")
    public ResponseEntity<LlaveModel> getLlaveById(@PathVariable Long id) {
        return llaveService.obtenerPorId(id)
                .map(llave -> new ResponseEntity<>(llave, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 4. Actualizar una llave existente
    @PutMapping("/{id}")
    public ResponseEntity<LlaveModel> actualizarLlave(@PathVariable Long id, @Valid @RequestBody LlaveModel llave) {
        try {
            LlaveModel llaveActualizada = llaveService.actualizarLlave(id, llave);
            return new ResponseEntity<>(llaveActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 5. Eliminar una llave
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLlave(@PathVariable Long id) {
        try {
            llaveService.eliminarLlave(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content es lo ideal para deletes exitosos
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 6. Buscar por juego ID: /api/llaves/buscar-juego?idJuego=1
    @GetMapping("/buscar-juego")
    public ResponseEntity<List<LlaveModel>> buscarPorJuego(@RequestParam("idJuego") Long idJuego) {
        List<LlaveModel> llaves = llaveService.buscarPorJuego(idJuego);
        if (llaves.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 si no hay resultados
        }
        return new ResponseEntity<>(llaves, HttpStatus.OK);
    }
}